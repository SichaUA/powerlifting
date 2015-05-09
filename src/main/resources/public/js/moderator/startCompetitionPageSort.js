function SQSort(l, r) {
    //Disqualified
    if(l.participantStatus() == 2 && r.participantStatus() != 2) return 1;
    if(l.participantStatus() != 2 && r.participantStatus() == 2) return -1;
    if(l.participantStatus() == 2 && r.participantStatus() == 2) return ordinalNumberSort(l, r);

    //Doctor
    if(l.participantStatus() == 3 && r.participantStatus() == 1) return 1;
    if(l.participantStatus() == 1 && r.participantStatus() == 3) return -1;
    if(l.participantStatus() == 3 && r.participantStatus() == 3) return ordinalNumberSort(l, r);

    if(l.SQThirdStatus() > 1 && r.SQThirdStatus() > 1 && l.SQThirdStatus() != 6 && r.SQThirdStatus() != 6) {
        return totalSort(l, r);
    }

    //Third attempt status comparison
    if(l.SQThirdStatus() > 1 && l.SQThirdStatus() != 6  && r.SQThirdStatus() <=1) return 1;
    if(l.SQThirdStatus() <= 1 && r.SQThirdStatus() > 1 && r.SQThirdStatus() != 6) return -1;
    if(l.SQThirdStatus() == r.SQThirdStatus() && l.SQThirdStatus() == 1) {
        if(l.SQThird() > r.SQThird())
            return 1;
        else if(l.SQThird() < r.SQThird())
            return -1;
        else
            return ordinalNumberSort(l, r);
    }

    //Judge mistake third attempt
    if(l.SQThirdStatus() == 6 && r.SQThirdStatus() > 1) return -1;
    if(l.SQThirdStatus() == 6 && r.SQThirdStatus() == 1) return 1;
    if(l.SQThirdStatus() > 1 && r.SQThirdStatus() == 6) return 1;
    if(l.SQThirdStatus() == 1 && r.SQThirdStatus() == 6) return -1;
    if(l.SQThirdStatus() == 6 && r.SQThirdStatus() == 6) {
        if(l.SQThird() > r.SQThird())
            return 1;
        else
            return -1;
    }

    //Second attempt status comparison
    if(l.SQSecondStatus() > 1 && l.SQSecondStatus != 6 && r.SQSecondStatus() <=1) return 1;
    if(l.SQSecondStatus() <= 1 && r.SQSecondStatus() > 1 && r.SQSecondStatus != 6) return -1;
    if(l.SQSecondStatus() == r.SQSecondStatus() && l.SQSecondStatus() == 1) {
        if(l.SQSecond() > r.SQSecond())
            return 1;
        else if(l.SQSecond() < r.SQSecond())
            return -1;
        else
            return ordinalNumberSort(l, r);
    }

    //Judge mistake second attempt
    if(l.SQSecondStatus() == 6 && r.SQSecondStatus() > 1) return -1;
    if(l.SQSecondStatus() == 6 && r.SQSecondStatus() == 1) return 1;
    if(l.SQSecondStatus() > 1 && r.SQSecondStatus() == 6) return 1;
    if(l.SQSecondStatus() == 1 && r.SQSecondStatus() == 6) return -1;
    if(l.SQSecondStatus() == 6 && r.SQSecondStatus() == 6) {
        if(l.SQSecond() > r.SQSecond())
            return 1;
        else
            return -1;
    }

    //First attempt status comparison
    if(l.SQFirstStatus() > 1 && l.SQFirstStatus != 6 && r.SQFirstStatus() <=1) return 1;
    if(l.SQFirstStatus() <= 1 && r.SQFirstStatus() > 1 && r.SQFirstStatus != 6) return -1;
    if(l.SQFirstStatus() == r.SQFirstStatus() && l.SQFirstStatus() == 1) {
        if(l.SQFirst() > r.SQFirst())
            return 1;
        else if(l.SQFirst() < r.SQFirst())
            return -1;
        else
            return ordinalNumberSort(l, r);
    }

    //Judge mistake first attempt
    if(l.SQFirstStatus() == 6 && r.SQFirstStatus() > 1) return -1;
    if(l.SQFirstStatus() == 6 && r.SQFirstStatus() == 1) return 1;
    if(l.SQFirstStatus() > 1 && r.SQFirstStatus() == 6) return 1;
    if(l.SQFirstStatus() == 1 && r.SQFirstStatus() == 6) return -1;
    if(l.SQFirstStatus() == 6 && r.SQFirstStatus() == 6) {
        if(l.SQFirst() > r.SQFirst())
            return 1;
        else
            return -1;
    }

    return ordinalNumberSort(l, r);
}

function BPSort(l, r) {
    //Disqualified
    if(l.participantStatus() == 2 && r.participantStatus() != 2) return 1;
    if(l.participantStatus() != 2 && r.participantStatus() == 2) return -1;
    if(l.participantStatus() == 2 && r.participantStatus() == 2) return ordinalNumberSort(l, r);

    //Doctor
    if(l.participantStatus() == 3 && r.participantStatus() == 1) return 1;
    if(l.participantStatus() == 1 && r.participantStatus() == 3) return -1;
    if(l.participantStatus() == 3 && r.participantStatus() == 3) return ordinalNumberSort(l, r);

    if(l.BPThirdStatus() > 1 && r.BPThirdStatus() > 1 && l.BPThirdStatus() != 6 && r.BPThirdStatus() != 6) {
        return totalSort(l, r);
    }

    //Third attempt status comparison
    if(l.BPThirdStatus() > 1 && l.BPThirdStatus() != 6  && r.BPThirdStatus() <=1) return 1;
    if(l.BPThirdStatus() <= 1 && r.BPThirdStatus() > 1 && r.BPThirdStatus() != 6) return -1;
    if(l.BPThirdStatus() == r.BPThirdStatus() && l.BPThirdStatus() == 1) {
        if(l.BPThird() > r.BPThird())
            return 1;
        else if(l.BPThird() < r.BPThird())
            return -1;
        else
            return ordinalNumberSort(l, r);
    }

    //Judge mistake third attempt
    if(l.BPThirdStatus() == 6 && r.BPThirdStatus() > 1) return -1;
    if(l.BPThirdStatus() == 6 && r.BPThirdStatus() == 1) return 1;
    if(l.BPThirdStatus() > 1 && r.BPThirdStatus() == 6) return 1;
    if(l.BPThirdStatus() == 1 && r.BPThirdStatus() == 6) return -1;
    if(l.BPThirdStatus() == 6 && r.BPThirdStatus() == 6) {
        if(l.BPThird() > r.BPThird())
            return 1;
        else
            return -1;
    }

    //Second attempt status comparison
    if(l.BPSecondStatus() > 1 && l.BPSecondStatus != 6 && r.BPSecondStatus() <=1) return 1;
    if(l.BPSecondStatus() <= 1 && r.BPSecondStatus() > 1 && r.BPSecondStatus != 6) return -1;
    if(l.BPSecondStatus() == r.BPSecondStatus() && l.BPSecondStatus() == 1) {
        if(l.BPSecond() > r.BPSecond())
            return 1;
        else if(l.BPSecond() < r.BPSecond())
            return -1;
        else
            return ordinalNumberSort(l, r);
    }

    //Judge mistake second attempt
    if(l.BPSecondStatus() == 6 && r.BPSecondStatus() > 1) return -1;
    if(l.BPSecondStatus() == 6 && r.BPSecondStatus() == 1) return 1;
    if(l.BPSecondStatus() > 1 && r.BPSecondStatus() == 6) return 1;
    if(l.BPSecondStatus() == 1 && r.BPSecondStatus() == 6) return -1;
    if(l.BPSecondStatus() == 6 && r.BPSecondStatus() == 6) {
        if(l.BPSecond() > r.BPSecond())
            return 1;
        else
            return -1;
    }

    //First attempt status comparison
    if(l.BPFirstStatus() > 1 && l.BPFirstStatus != 6 && r.BPFirstStatus() <=1) return 1;
    if(l.BPFirstStatus() <= 1 && r.BPFirstStatus() > 1 && r.BPFirstStatus != 6) return -1;
    if(l.BPFirstStatus() == r.BPFirstStatus() && l.BPFirstStatus() == 1) {
        if(l.BPFirst() > r.BPFirst())
            return 1;
        else if(l.BPFirst() < r.BPFirst())
            return -1;
        else
            return ordinalNumberSort(l, r);
    }

    //Judge mistake first attempt
    if(l.BPFirstStatus() == 6 && r.BPFirstStatus() > 1) return -1;
    if(l.BPFirstStatus() == 6 && r.BPFirstStatus() == 1) return 1;
    if(l.BPFirstStatus() > 1 && r.BPFirstStatus() == 6) return 1;
    if(l.BPFirstStatus() == 1 && r.BPFirstStatus() == 6) return -1;
    if(l.BPFirstStatus() == 6 && r.BPFirstStatus() == 6) {
        if(l.BPFirst() > r.BPFirst())
            return 1;
        else
            return -1;
    }

    return ordinalNumberSort(l, r);
}

function DLSort(l, r) {
    //Disqualified
    if(l.participantStatus() == 2 && r.participantStatus() != 2) return 1;
    if(l.participantStatus() != 2 && r.participantStatus() == 2) return -1;
    if(l.participantStatus() == 2 && r.participantStatus() == 2) return ordinalNumberSort(l, r);

    //Doctor
    if(l.participantStatus() == 3 && r.participantStatus() == 1) return 1;
    if(l.participantStatus() == 1 && r.participantStatus() == 3) return -1;
    if(l.participantStatus() == 3 && r.participantStatus() == 3) return ordinalNumberSort(l, r);

    if(l.DLThirdStatus() > 1 && r.DLThirdStatus() > 1 && l.DLThirdStatus() != 6 && r.DLThirdStatus() != 6) {
        return totalSort(l, r);
    }

    //Third attempt status comparison
    if(l.DLThirdStatus() > 1 && l.DLThirdStatus() != 6  && r.DLThirdStatus() <=1) return 1;
    if(l.DLThirdStatus() <= 1 && r.DLThirdStatus() > 1 && r.DLThirdStatus() != 6) return -1;
    if(l.DLThirdStatus() == r.DLThirdStatus() && l.DLThirdStatus() == 1) {
        if(l.DLThird() > r.DLThird())
            return 1;
        else if(l.DLThird() < r.DLThird())
            return -1;
        else
            return ordinalNumberSort(l, r);
    }

    //Judge mistake third attempt
    if(l.DLThirdStatus() == 6 && r.DLThirdStatus() > 1) return -1;
    if(l.DLThirdStatus() == 6 && r.DLThirdStatus() == 1) return 1;
    if(l.DLThirdStatus() > 1 && r.DLThirdStatus() == 6) return 1;
    if(l.DLThirdStatus() == 1 && r.DLThirdStatus() == 6) return -1;
    if(l.DLThirdStatus() == 6 && r.DLThirdStatus() == 6) {
        if(l.DLThird() > r.DLThird())
            return 1;
        else
            return -1;
    }

    //Second attempt status comparison
    if(l.DLSecondStatus() > 1 && l.DLSecondStatus != 6 && r.DLSecondStatus() <=1) return 1;
    if(l.DLSecondStatus() <= 1 && r.DLSecondStatus() > 1 && r.DLSecondStatus != 6) return -1;
    if(l.DLSecondStatus() == r.DLSecondStatus() && l.DLSecondStatus() == 1) {
        if(l.DLSecond() > r.DLSecond())
            return 1;
        else if(l.DLSecond() < r.DLSecond())
            return -1;
        else
            return ordinalNumberSort(l, r);
    }

    //Judge mistake second attempt
    if(l.DLSecondStatus() == 6 && r.DLSecondStatus() > 1) return -1;
    if(l.DLSecondStatus() == 6 && r.DLSecondStatus() == 1) return 1;
    if(l.DLSecondStatus() > 1 && r.DLSecondStatus() == 6) return 1;
    if(l.DLSecondStatus() == 1 && r.DLSecondStatus() == 6) return -1;
    if(l.DLSecondStatus() == 6 && r.DLSecondStatus() == 6) {
        if(l.DLSecond() > r.DLSecond())
            return 1;
        else
            return -1;
    }

    //First attempt status comparison
    if(l.DLFirstStatus() > 1 && l.DLFirstStatus != 6 && r.DLFirstStatus() <=1) return 1;
    if(l.DLFirstStatus() <= 1 && r.DLFirstStatus() > 1 && r.DLFirstStatus != 6) return -1;
    if(l.DLFirstStatus() == r.DLFirstStatus() && l.DLFirstStatus() == 1) {
        if(l.DLFirst() > r.DLFirst())
            return 1;
        else if(l.DLFirst() < r.DLFirst())
            return -1;
        else
            return ordinalNumberSort(l, r);
    }

    //Judge mistake first attempt
    if(l.DLFirstStatus() == 6 && r.DLFirstStatus() > 1) return -1;
    if(l.DLFirstStatus() == 6 && r.DLFirstStatus() == 1) return 1;
    if(l.DLFirstStatus() > 1 && r.DLFirstStatus() == 6) return 1;
    if(l.DLFirstStatus() == 1 && r.DLFirstStatus() == 6) return -1;
    if(l.DLFirstStatus() == 6 && r.DLFirstStatus() == 6) {
        if(l.DLFirst() > r.DLFirst())
            return 1;
        else
            return -1;
    }

    return ordinalNumberSort(l, r);
}

function ordinalNumberSort(l, r) {
    if(l.ordinalNumber > r.ordinalNumber)
        return 1;

    return -1;
}

function totalSort(l, r) {
    if(l.currentTotal() > r.currentTotal())
        return -1;

    return 1;
}