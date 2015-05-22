package com.powerlifting.reports;

import com.powerlifting.controllers.registered.model.*;
import com.powerlifting.dao.CompetitionDao;
import com.powerlifting.dao.JudgeDao;
import javafx.util.Pair;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.sql.Date;
import java.text.DateFormat;
import java.util.*;

public class ReportsGenerating {
    @Autowired
    CompetitionDao competitionDao;
    @Autowired
    JudgeDao judgeDao;

    public String generateMainProtocol(Integer competitionId) {
        Competition competition = competitionDao.getCompetition(competitionId).get();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sample sheet");

        HSSFCellStyle style = workbook.createCellStyle();
        HSSFCellStyle categoryStyle = workbook.createCellStyle();
        HSSFCellStyle headerStyle = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();

        int rowNum = 0;
        int cellNum = 0;
        ReportRegionPointsSum regionsPoints = new ReportRegionPointsSum();

//        Set competition name
        Row firstRow = sheet.createRow(rowNum++);
        Cell firstRowFirstCell = firstRow.createCell(cellNum);
        firstRowFirstCell.setCellValue(competition.getName());
        style.setAlignment(CellStyle.ALIGN_CENTER);
        font.setBold(true);
        style.setFont(font);
        firstRowFirstCell.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));

//        Male information
        if(competition.getGender() == 1 || competition.getGender() == 2) {
//            row with string male
            Row maleRow = sheet.createRow(rowNum++);
            Cell maleCell = maleRow.createCell(cellNum);
            maleCell.setCellValue("Male");
            maleCell.setCellStyle(style);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 13));

//            Set table header
            Row row = sheet.createRow(rowNum++);
            headerStyle.setBorderTop((short)1);
            headerStyle.setBorderBottom((short) 1);
            headerStyle.setBorderLeft((short) 1);
            headerStyle.setBorderRight((short) 1);
            Object[] data = new Object[]{"#", "Name", "Birthday", "Title/Discharge", "Region", "Own weight", "Wilks coef.", "SQ", "BP", "DL", "Total", "Total Wilks", "Points", "Coaches"};
            for (Object obj : data) {
                Cell cell = row.createCell(cellNum++);
                cell.setCellValue((String)obj);
                cell.setCellStyle(headerStyle);
            }
            cellNum = 0;

            final List<Sequence> competitionSequences = competitionDao.getCompetitionSequences(competitionId);
            for(Iterator<Sequence> i = competitionSequences.iterator(); i.hasNext(); ) {
                final Sequence currSequence = i.next();

                if(currSequence.getCategories().get(0).getKey().getGender() == 1) {
                    for (Iterator<Pair<WeightCategory, AgeGroup>> j = currSequence.getCategories().iterator(); j.hasNext(); ) {
                        final Pair<WeightCategory, AgeGroup> weightCategoryAgeGroupPair = j.next();

                        row = sheet.createRow(rowNum++);
                        Cell cell = row.createCell(0);
                        cell.setCellValue(weightCategoryAgeGroupPair.getKey().getName() + " " + weightCategoryAgeGroupPair.getValue().getDescription());

                        categoryStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
                        categoryStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                        cell.setCellStyle(categoryStyle);

                        sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 13));

                        final List<ReportParticipantInfo> participants = competitionDao.getCompetitionParticipantsInWeightCategories(competitionId, weightCategoryAgeGroupPair.getKey().getCategoryId(), weightCategoryAgeGroupPair.getValue().getGroupId());
                        int participantNumber = 1;
                        for (Iterator<ReportParticipantInfo> k = participants.iterator(); k.hasNext(); participantNumber++) {
                            ReportParticipantInfo currParticipantInfo = k.next();

                            row = sheet.createRow(rowNum++);
                            cell = row.createCell(0);
                            cell.setCellValue(participantNumber);

                            cell = row.createCell(1);
                            cell.setCellValue(currParticipantInfo.getName());

                            //                        dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd/mm/yyyy"));
                            cell = row.createCell(2);
                            cell.setCellValue(currParticipantInfo.getBirthday().toString());
                            //                        cell.setCellStyle(dateStyle);


                            cell = row.createCell(3);
                            cell.setCellValue(currParticipantInfo.getTitle());

                            cell = row.createCell(4);
                            cell.setCellValue(currParticipantInfo.getFrom());

                            cell = row.createCell(5);
                            cell.setCellValue(currParticipantInfo.getOwnWeight());

                            cell = row.createCell(6);
                            cell.setCellValue(currParticipantInfo.getWilks());

                            cell = row.createCell(7);
                            cell.setCellValue(currParticipantInfo.getSQ());

                            cell = row.createCell(8);
                            cell.setCellValue(currParticipantInfo.getBP());

                            cell = row.createCell(9);
                            cell.setCellValue(currParticipantInfo.getDL());

                            cell = row.createCell(10);
                            cell.setCellValue(currParticipantInfo.getTotalSum());

                            cell = row.createCell(11);
                            cell.setCellValue(currParticipantInfo.getTotalWilks());

                            if (currParticipantInfo.getParticipantStatus() == 1) {
                                currParticipantInfo.setPoints(setPoints(participantNumber));
                                regionsPoints.addPoint(currParticipantInfo.getFrom(), setPoints(participantNumber));
                            } else {
                                currParticipantInfo.setPoints(0);
                            }
                            cell = row.createCell(12);
                            cell.setCellValue(currParticipantInfo.getPoints());

                            cell = row.createCell(13);
                            cell.setCellValue(currParticipantInfo.getCoaches());
                        }

                        final List<JudgeAllInfo> allSequenceJudges = judgeDao.getAllSequenceJudges(currSequence.getSequenceId());
                        String judges = "";
                        for (Iterator<JudgeAllInfo> k = allSequenceJudges.iterator(); k.hasNext(); ) {
                            final JudgeAllInfo judgeAllInfo = k.next();
                            judges += judgeAllInfo.getJudgeType().getName() + ": " + judgeAllInfo.getUser().getSecondName() + " " + judgeAllInfo.getUser().getFirstName() + " " +
                                    " (" + judgeAllInfo.getJudgeCategory().getCategory() + "); ";
                        }

                        row = sheet.createRow(rowNum++);
                        cell = row.createCell(0);
                        cell.setCellValue(judges);

                        categoryStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
                        categoryStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

                        cell.setCellStyle(categoryStyle);
                        sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 13));
                    }
                }
            }
        }

        rowNum += 3;

        //        Female information
        if(competition.getGender() == 0 || competition.getGender() == 2) {
//            row with string male
            Row maleRow = sheet.createRow(rowNum++);
            Cell maleCell = maleRow.createCell(cellNum);
            maleCell.setCellValue("Female");
            maleCell.setCellStyle(style);
            sheet.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum-1, 0, 13));

//            Set table header
            Row row = sheet.createRow(rowNum++);
            headerStyle.setBorderTop((short)1);
            headerStyle.setBorderBottom((short)1);
            headerStyle.setBorderLeft((short)1);
            headerStyle.setBorderRight((short)1);
            Object[] data = new Object[]{"#", "Name", "Birthday", "Title/Discharge", "Region", "Own weight", "Wilks coef.", "SQ", "BP", "DL", "Total", "Total Wilks", "Points", "Coaches"};
            for (Object obj : data) {
                Cell cell = row.createCell(cellNum++);
                cell.setCellValue((String)obj);
                cell.setCellStyle(headerStyle);
            }
            cellNum = 0;

            final List<Sequence> competitionSequences = competitionDao.getCompetitionSequences(competitionId);
            for(Iterator<Sequence> i = competitionSequences.iterator(); i.hasNext(); ) {
                final Sequence currSequence = i.next();

                if(currSequence.getCategories().get(0).getKey().getGender() == 0) {
                    for (Iterator<Pair<WeightCategory, AgeGroup>> j = currSequence.getCategories().iterator(); j.hasNext(); ) {
                        final Pair<WeightCategory, AgeGroup> weightCategoryAgeGroupPair = j.next();

                        row = sheet.createRow(rowNum++);
                        Cell cell = row.createCell(0);
                        cell.setCellValue(weightCategoryAgeGroupPair.getKey().getName() + " " + weightCategoryAgeGroupPair.getValue().getDescription());

                        categoryStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
                        categoryStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                        cell.setCellStyle(categoryStyle);

                        sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 13));

                        final List<ReportParticipantInfo> participants = competitionDao.getCompetitionParticipantsInWeightCategories(competitionId, weightCategoryAgeGroupPair.getKey().getCategoryId(), weightCategoryAgeGroupPair.getValue().getGroupId());
                        int participantNumber = 1;
                        for (Iterator<ReportParticipantInfo> k = participants.iterator(); k.hasNext(); participantNumber++) {
                            ReportParticipantInfo currParticipantInfo = k.next();

                            row = sheet.createRow(rowNum++);
                            cell = row.createCell(0);
                            cell.setCellValue(participantNumber);

                            cell = row.createCell(1);
                            cell.setCellValue(currParticipantInfo.getName());

                            //                        dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd/mm/yyyy"));
                            cell = row.createCell(2);
                            cell.setCellValue(currParticipantInfo.getBirthday().toString());
                            //                        cell.setCellStyle(dateStyle);


                            cell = row.createCell(3);
                            cell.setCellValue(currParticipantInfo.getTitle());

                            cell = row.createCell(4);
                            cell.setCellValue(currParticipantInfo.getFrom());

                            cell = row.createCell(5);
                            cell.setCellValue(currParticipantInfo.getOwnWeight());

                            cell = row.createCell(6);
                            cell.setCellValue(currParticipantInfo.getWilks());

                            cell = row.createCell(7);
                            cell.setCellValue(currParticipantInfo.getSQ());

                            cell = row.createCell(8);
                            cell.setCellValue(currParticipantInfo.getBP());

                            cell = row.createCell(9);
                            cell.setCellValue(currParticipantInfo.getDL());

                            cell = row.createCell(10);
                            cell.setCellValue(currParticipantInfo.getTotalSum());

                            cell = row.createCell(11);
                            cell.setCellValue(currParticipantInfo.getTotalWilks());

                            if (currParticipantInfo.getParticipantStatus() == 1) {
                                currParticipantInfo.setPoints(setPoints(participantNumber));
                                regionsPoints.addPoint(currParticipantInfo.getFrom(), setPoints(participantNumber));
                            } else {
                                currParticipantInfo.setPoints(0);
                            }
                            cell = row.createCell(12);
                            cell.setCellValue(currParticipantInfo.getPoints());

                            cell = row.createCell(13);
                            cell.setCellValue(currParticipantInfo.getCoaches());
                        }

                        final List<JudgeAllInfo> allSequenceJudges = judgeDao.getAllSequenceJudges(currSequence.getSequenceId());
                        String judges = "";
                        for (Iterator<JudgeAllInfo> k = allSequenceJudges.iterator(); k.hasNext(); ) {
                            final JudgeAllInfo judgeAllInfo = k.next();
                            judges += judgeAllInfo.getJudgeType().getName() + ": " + judgeAllInfo.getUser().getSecondName() + " " + judgeAllInfo.getUser().getFirstName() + " " +
                                    " (" + judgeAllInfo.getJudgeCategory().getCategory() + "); ";
                        }

                        row = sheet.createRow(rowNum++);
                        cell = row.createCell(0);
                        cell.setCellValue(judges);

                        categoryStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
                        categoryStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

                        cell.setCellStyle(categoryStyle);
                        sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 13));
                    }
                }
            }
        }

        rowNum += 2;

        Row row = sheet.createRow(rowNum++);
        Cell cell = row.createCell(0);
        cell.setCellValue("Regions:");

        int regionNum = 1;
        for(Iterator<RegionWithPoints> i = regionsPoints.getRegions().iterator(); i.hasNext(); regionNum++) {
            final RegionWithPoints regionWithPoints = i.next();

            String regionStr = regionNum + ". " + regionWithPoints.getRegion() + " " + regionWithPoints.getPoints().stream().mapToInt(Integer::intValue).sum() +
                    " (" + regionWithPoints.getPoints().toString() + ")";

            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(regionStr);
        }


        for(int i = 0; i < 14; i++) {
            sheet.autoSizeColumn(i);
        }
        try {
            FileOutputStream out =
                    new FileOutputStream(new File("C:\\powerlifting\\powerlifting\\src\\main\\resources\\public\\reports\\" + competitionId + ".xls"));
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "";
    }

    private Integer setPoints(Integer participantNumber) {
        switch (participantNumber) {
            case 1:
                return 12;
            case 2:
                return 9;
            case 3:
                return 8;
            case 4:
                return 7;
            case 5:
                return 6;
            case 6:
                return 5;
            case 7:
                return 4;
            case 8:
                return 3;
            case 9:
                return 2;
        }

        return 1;
    }

}
