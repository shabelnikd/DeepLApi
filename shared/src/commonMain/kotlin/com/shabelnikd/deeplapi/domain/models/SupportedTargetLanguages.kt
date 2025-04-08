package com.shabelnikd.deeplapi.domain.models

enum class SupportedTargetLanguages(val uiName: String, val requestFieldName: String) {
    AR(uiName = "Arabic", requestFieldName = "AR"),
    BG(uiName = "Bulgarian", requestFieldName = "BG"),
    CS(uiName = "Czech", requestFieldName = "CS"),
    DA(uiName = "Danish", requestFieldName = "DA"),
    DE(uiName = "German", requestFieldName = "DE"),
    EL(uiName = "Greek", requestFieldName = "EL"),
    EN(uiName = "English", requestFieldName = "EN"), // Unspecified, for compatibility
    ES(uiName = "Spanish", requestFieldName = "ES"),
    ET(uiName = "Estonian", requestFieldName = "ET"),
    FI(uiName = "Finnish", requestFieldName = "FI"),
    FR(uiName = "French", requestFieldName = "FR"),
    HU(uiName = "Hungarian", requestFieldName = "HU"),
    ID(uiName = "Indonesian", requestFieldName = "ID"),
    IT(uiName = "Italian", requestFieldName = "IT"),
    JA(uiName = "Japanese", requestFieldName = "JA"),
    KO(uiName = "Korean", requestFieldName = "KO"),
    LT(uiName = "Lithuanian", requestFieldName = "LT"),
    LV(uiName = "Latvian", requestFieldName = "LV"),
    NB(uiName = "Norwegian Bokmål", requestFieldName = "NB"),
    NL(uiName = "Dutch", requestFieldName = "NL"),
    PL(uiName = "Polish", requestFieldName = "PL"),
    PT(uiName = "Portuguese", requestFieldName = "PT"), // Unspecified, for compatibility
    RO(uiName = "Romanian", requestFieldName = "RO"),
    RU(uiName = "Russian", requestFieldName = "RU"),
    SK(uiName = "Slovak", requestFieldName = "SK"),
    SL(uiName = "Slovenian", requestFieldName = "SL"),
    SV(uiName = "Swedish", requestFieldName = "SV"),
    TR(uiName = "Turkish", requestFieldName = "TR"),
    UK(uiName = "Ukrainian", requestFieldName = "UK"),
    ZH(uiName = "Chinese", requestFieldName = "ZH"), // Unspecified, for compatibility
}