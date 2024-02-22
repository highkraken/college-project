package com.example.collegeproject.utils

enum class UnitType(val unit: String, val acronym: String) {
    NONE("None", "-"),
    BAG("Bag", "Bg"),
    BOTTLE("Bottle", "Btl"),
    BOX("Box", "Bx"),
    BUNDLE("Bundle", "Bdl"),
    CAN("Can", "Cn"),
    CARTON("Carton", "Ctn"),
    DOZEN("Dozen", "Dzn"),
    GRAM("Gram", "Gm"),
    KILOGRAM("Kilogram", "Kg"),
    LITRE("Litre", "Ltr"),
    METER("Meter", "Mtr"),
    MILLILITRE("Millilitre", "Ml"),
    NUMBER("Number", "Nos"),
    PIECE("Piece", "Pcs"),
    QUINTAL("Quintal", "Qtl"),
    SQUARE_FEET("Square Feet", "Sqf");
}