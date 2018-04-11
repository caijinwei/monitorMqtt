package com.wecon.common.util;

/**
 * Created by linkaixun on 2015/12/21.
 */
public class CountriesUtil
{
    /***
     * ISO 3166 2位字母代码转ISO 3166 3位数字代码
     *
     * @param conutry 中国编码
     * @return
     */
    public static int conutryToCode(String conutry)
    {
        if (conutry == null || conutry.isEmpty())
        {
            return 0;
        }
        String strCountry = conutry.toUpperCase();
        switch (strCountry)
        {
            case "AF": //阿富汗
                return 4;
            case "AL": //阿尔巴尼亚
                return 8;
            case "DZ": //阿尔及利亚
                return 12;
            case "AS": //美属萨摩亚
                return 16;
            case "AD": //安道尔
                return 20;
            case "AO": //安哥拉
                return 24;
            case "AI": //安圭拉
                return 660;
            case "AQ": //南极洲
                return 10;
            case "AG": //安提瓜和巴布达
                return 28;
            case "AR": //阿根廷
                return 32;
            case "AM": //亚美尼亚
                return 51;
            case "AW": //阿鲁巴
                return 533;
            case "AU": //澳大利亚
                return 36;
            case "AT": //奥地利
                return 40;
            case "AZ": //阿塞拜疆
                return 31;
            case "BS": //巴哈马
                return 44;
            case "BH": //巴林
                return 48;
            case "BD": //孟加拉国
                return 50;
            case "BB": //巴巴多斯
                return 52;
            case "BY": //白俄罗斯
                return 112;
            case "BE": //比利时
                return 56;
            case "BZ": //伯利兹
                return 84;
            case "BJ": //贝宁
                return 204;
            case "BM": //百慕大
                return 60;
            case "BT": //不丹
                return 64;
            case "BO": //玻利维亚
                return 68;
            case "BA": //波斯尼亚和黑塞哥维那
                return 70;
            case "BW": //博茨瓦纳
                return 72;
            case "BV": //布韦岛
                return 74;
            case "BR": //巴西
                return 76;
            case "IO": //英属印度洋领地
                return 86;
            case "VG": //英属维尔京群岛
                return 92;
            case "BN": //文莱
                return 96;
            case "BG": //保加利亚
                return 100;
            case "BF": //布基纳法索
                return 854;
            case "MM": //缅甸
                return 104;
            case "BI": //布隆迪
                return 108;
            case "CV": //佛得角
                return 132;
            case "KH": //柬埔寨
                return 116;
            case "CM": //喀麦隆
                return 120;
            case "CA": //加拿大
                return 124;
            case "KY": //开曼群岛
                return 136;
            case "CF": //中非共和国
                return 140;
            case "TD": //乍得
                return 148;
            case "CL": //智利
                return 152;
            case "CX": //圣诞岛
                return 162;
            case "CC": //科科斯（基林）群岛
                return 166;
            case "CO": //哥伦比亚
                return 170;
            case "KM": //科摩罗
                return 174;
            case "CD": //民主刚果
                return 180;
            case "CG": //刚果
                return 178;
            case "CK": //库克群岛
                return 184;
            case "CR": //哥斯达黎加
                return 188;
            case "CI": //科特迪瓦
                return 384;
            case "HR": //克罗地亚
                return 191;
            case "CU": //古巴
                return 192;
            case "CW": //库拉索
                return 531;
            case "CY": //塞浦路斯
                return 196;
            case "CZ": //捷克
                return 203;
            case "DK": //丹麦
                return 208;
            case "DJ": //吉布提
                return 262;
            case "DM": //多米尼克
                return 212;
            case "DO": //多米尼加
                return 214;
            case "EC": //厄瓜多尔
                return 218;
            case "EG": //埃及
                return 818;
            case "SV": //萨尔瓦多
                return 222;
            case "GQ": //赤道几内亚
                return 226;
            case "ER": //厄立特里亚
                return 232;
            case "EE": //爱沙尼亚
                return 233;
            case "ET": //埃塞俄比亚
                return 231;
            case "FK": //福克兰群岛
                return 238;
            case "FO": //法罗群岛
                return 234;
            case "FJ": //斐济
                return 242;
            case "FI": //芬兰
                return 246;
            case "FR": //法国
                return 250;
            case "FX": //法国
                return 249;
            case "GF": //法属圭亚那
                return 254;
            case "PF": //法属波利尼西亚
                return 258;
            case "TF": //法属南部领地
                return 260;
            case "GA": //加蓬
                return 266;
            case "GM": //冈比亚
                return 270;
            case "PS": //巴勒斯坦
                return 275;
            case "GE": //格鲁吉亚
                return 268;
            case "DE": //德国
                return 276;
            case "GH": //加纳
                return 288;
            case "GI": //直布罗陀
                return 292;
            case "GR": //希腊
                return 300;
            case "GL": //格陵兰
                return 304;
            case "GD": //格林纳达
                return 308;
            case "GP": //瓜德罗普
                return 312;
            case "GU": //关岛
                return 316;
            case "GT": //危地马拉
                return 320;
            case "GG": //根西
                return 831;
            case "GN": //几内亚
                return 324;
            case "GW": //几内亚比绍
                return 624;
            case "GY": //圭亚那
                return 328;
            case "HT": //海地
                return 332;
            case "HM": //赫德岛和麦克唐纳群岛
                return 334;
            case "VA": //梵蒂冈
                return 336;
            case "HN": //洪都拉斯
                return 340;
            case "HK": //香港
                return 344;
            case "HU": //匈牙利
                return 348;
            case "IS": //冰岛
                return 352;
            case "IN": //印度
                return 356;
            case "ID": //印尼
                return 360;
            case "IR": //伊朗
                return 364;
            case "IQ": //伊拉克
                return 368;
            case "IE": //爱尔兰
                return 372;
            case "IM": //马恩岛
                return 833;
            case "IL": //以色列
                return 376;
            case "IT": //意大利
                return 380;
            case "JM": //牙买加
                return 388;
            case "JP": //日本
                return 392;
            case "JE": //泽西
                return 832;
            case "JO": //约旦
                return 400;
            case "KZ": //哈萨克斯坦
                return 398;
            case "KE": //肯尼亚
                return 404;
            case "KI": //基里巴斯
                return 296;
            case "KP": //朝鲜
                return 408;
            case "KR": //韩国
                return 410;
            case "KW": //科威特
                return 414;
            case "KG": //吉尔吉斯斯坦
                return 417;
            case "LA": //老挝
                return 418;
            case "LV": //拉脱维亚
                return 428;
            case "LB": //黎巴嫩
                return 422;
            case "LS": //莱索托
                return 426;
            case "LR": //利比里亚
                return 430;
            case "LY": //利比亚
                return 434;
            case "LI": //列支敦士登
                return 438;
            case "LT": //立陶宛
                return 440;
            case "LU": //卢森堡
                return 442;
            case "MO": //澳门
                return 446;
            case "MK": //马其顿
                return 807;
            case "MG": //马达加斯加
                return 450;
            case "MW": //马拉维
                return 454;
            case "MY": //马来西亚
                return 458;
            case "MV": //马尔代夫
                return 462;
            case "ML": //马里
                return 466;
            case "MT": //马耳他
                return 470;
            case "MH": //马绍尔群岛
                return 584;
            case "MQ": //马提尼克
                return 474;
            case "MR": //毛里塔尼亚
                return 478;
            case "MU": //毛里求斯
                return 480;
            case "YT": //马约特
                return 175;
            case "MX": //墨西哥
                return 484;
            case "FM": //密克罗尼西亚联邦
                return 583;
            case "MD": //摩尔多瓦
                return 498;
            case "MC": //摩纳哥
                return 492;
            case "MN": //蒙古国
                return 496;
            case "ME": //黑山
                return 499;
            case "MS": //蒙特塞拉特
                return 500;
            case "MA": //摩洛哥
                return 504;
            case "MZ": //莫桑比克
                return 508;
            case "NA": //纳米比亚
                return 516;
            case "NR": //瑙鲁
                return 520;
            case "NP": //尼泊尔
                return 524;
            case "NL": //荷兰
                return 528;
            case "NC": //新喀里多尼亚
                return 540;
            case "NZ": //新西兰
                return 554;
            case "NI": //尼加拉瓜
                return 558;
            case "NE": //尼日尔
                return 562;
            case "NG": //尼日利亚
                return 566;
            case "NU": //纽埃
                return 570;
            case "NF": //诺福克岛
                return 574;
            case "MP": //北马里亚纳群岛
                return 580;
            case "NO": //挪威
                return 578;
            case "OM": //阿曼
                return 512;
            case "PK": //巴基斯坦
                return 586;
            case "PW": //帕劳
                return 585;
            case "PA": //巴拿马
                return 591;
            case "PG": //巴布亚新几内亚
                return 598;
            case "CN": //中华人民共和国
                return 156;
            case "PY": //巴拉圭
                return 600;
            case "PE": //秘鲁
                return 604;
            case "PH": //菲律宾
                return 608;
            case "PN": //皮特凯恩群岛
                return 612;
            case "PL": //波兰
                return 616;
            case "PT": //葡萄牙
                return 620;
            case "PR": //波多黎各
                return 630;
            case "QA": //卡塔尔
                return 634;
            case "RE": //留尼汪
                return 638;
            case "RO": //罗马尼亚
                return 642;
            case "TW": //中华民国
                return 158;
            case "RU": //俄罗斯
                return 643;
            case "RW": //卢旺达
                return 646;
            case "BL": //圣巴泰勒米
                return 652;
            case "SH": //圣赫勒拿
                return 654;
            case "KN": //圣基茨和尼维斯
                return 659;
            case "LC": //圣卢西亚
                return 662;
            case "MF": //圣马丁行政区
                return 663;
            case "VC": //圣文森特和格林纳丁斯
                return 670;
            case "WS": //萨摩亚
                return 882;
            case "SM": //圣马力诺
                return 674;
            case "ST": //圣多美和普林西比
                return 678;
            case "SA": //沙特阿拉伯
                return 682;
            case "SN": //塞内加尔
                return 686;
            case "RS": //塞尔维亚
                return 688;
            case "SC": //塞舌尔
                return 690;
            case "SL": //塞拉利昂
                return 694;
            case "SG": //新加坡
                return 702;
            case "SX": //荷属圣马丁
                return 534;
            case "SK": //斯洛伐克
                return 703;
            case "SI": //斯洛文尼亚
                return 705;
            case "SB": //所罗门群岛
                return 90;
            case "SO": //索马里
                return 706;
            case "ZA": //南非
                return 710;
            case "GS": //南乔治亚和南桑威奇群岛
                return 239;
            case "SS": //南苏丹
                return 728;
            case "ES": //西班牙
                return 724;
            case "LK": //斯里兰卡
                return 144;
            case "SD": //苏丹
                return 729;
            case "SR": //苏里南
                return 740;
            case "PM": //圣皮埃尔和密克隆
                return 666;
            case "SZ": //斯威士兰
                return 748;
            case "SE": //瑞典
                return 752;
            case "CH": //瑞士
                return 756;
            case "SY": //叙利亚
                return 760;
            case "TJ": //塔吉克斯坦
                return 762;
            case "TZ": //坦桑尼亚
                return 834;
            case "TH": //泰国
                return 764;
            case "TL": //东帝汶
                return 626;
            case "TG": //多哥
                return 768;
            case "TK": //托克劳
                return 772;
            case "TO": //汤加
                return 776;
            case "TT": //特立尼达
                return 780;
            case "TN": //突尼斯
                return 788;
            case "TR": //土耳其
                return 792;
            case "TM": //土库曼斯坦
                return 795;
            case "TC": //特克斯和凯科斯群岛
                return 796;
            case "TV": //图瓦卢
                return 798;
            case "UG": //乌干达
                return 800;
            case "UA": //乌克兰
                return 804;
            case "AE": //阿联酋
                return 784;
            case "GB": //英国
                return 826;
            case "US": //美国
                return 840;
            case "UM": //美国本土外小岛屿
                return 581;
            case "UY": //乌拉圭
                return 858;
            case "UZ": //乌兹别克斯坦
                return 860;
            case "VU": //瓦努阿图
                return 548;
            case "VE": //委内瑞拉
                return 862;
            case "VN": //越南
                return 704;
            case "VI": //美属维京群岛
                return 850;
            case "WF": //瓦利斯和富图纳
                return 876;
            case "EH": //撒拉威阿拉伯民主共和国
                return 732;
            case "YE": //也门
                return 887;
            case "ZM": //赞比亚
                return 894;
            case "ZW": //津巴布韦
                return 716;
            default:
                return 156; //中国
        }
    }

    /***
     * ISO 3166 3位数字代码转ISO 3166 2位字母代码
     *
     * @param conutrycode
     * @return
     */
    public static String codeToConutry(int conutrycode)
    {
        switch (conutrycode)
        {
            case 4: //阿富汗
                return "AF";
            case 8: //阿尔巴尼亚
                return "AL";
            case 12: //阿尔及利亚
                return "DZ";
            case 16: //美属萨摩亚
                return "AS";
            case 20: //安道尔
                return "AD";
            case 24: //安哥拉
                return "AO";
            case 660: //安圭拉
                return "AI";
            case 10: //南极洲
                return "AQ";
            case 28: //安提瓜和巴布达
                return "AG";
            case 32: //阿根廷
                return "AR";
            case 51: //亚美尼亚
                return "AM";
            case 533: //阿鲁巴
                return "AW";
            case 36: //澳大利亚
                return "AU";
            case 40: //奥地利
                return "AT";
            case 31: //阿塞拜疆
                return "AZ";
            case 44: //巴哈马
                return "BS";
            case 48: //巴林
                return "BH";
            case 50: //孟加拉国
                return "BD";
            case 52: //巴巴多斯
                return "BB";
            case 112: //白俄罗斯
                return "BY";
            case 56: //比利时
                return "BE";
            case 84: //伯利兹
                return "BZ";
            case 204: //贝宁
                return "BJ";
            case 60: //百慕大
                return "BM";
            case 64: //不丹
                return "BT";
            case 68: //玻利维亚
                return "BO";
            case 70: //波斯尼亚和黑塞哥维那
                return "BA";
            case 72: //博茨瓦纳
                return "BW";
            case 74: //布韦岛
                return "BV";
            case 76: //巴西
                return "BR";
            case 86: //英属印度洋领地
                return "IO";
            case 92: //英属维尔京群岛
                return "VG";
            case 96: //文莱
                return "BN";
            case 100: //保加利亚
                return "BG";
            case 854: //布基纳法索
                return "BF";
            case 104: //缅甸
                return "MM";
            case 108: //布隆迪
                return "BI";
            case 132: //佛得角
                return "CV";
            case 116: //柬埔寨
                return "KH";
            case 120: //喀麦隆
                return "CM";
            case 124: //加拿大
                return "CA";
            case 136: //开曼群岛
                return "KY";
            case 140: //中非共和国
                return "CF";
            case 148: //乍得
                return "TD";
            case 152: //智利
                return "CL";
            case 156: //中国
                return "CN";
            case 162: //圣诞岛
                return "CX";
            case 166: //科科斯（基林）群岛
                return "CC";
            case 170: //哥伦比亚
                return "CO";
            case 174: //科摩罗
                return "KM";
            case 180: //民主刚果
                return "CD";
            case 178: //刚果
                return "CG";
            case 184: //库克群岛
                return "CK";
            case 188: //哥斯达黎加
                return "CR";
            case 384: //科特迪瓦
                return "CI";
            case 191: //克罗地亚
                return "HR";
            case 192: //古巴
                return "CU";
            case 531: //库拉索
                return "CW";
            case 196: //塞浦路斯
                return "CY";
            case 203: //捷克
                return "CZ";
            case 208: //丹麦
                return "DK";
            case 262: //吉布提
                return "DJ";
            case 212: //多米尼克
                return "DM";
            case 214: //多米尼加
                return "DO";
            case 218: //厄瓜多尔
                return "EC";
            case 818: //埃及
                return "EG";
            case 222: //萨尔瓦多
                return "SV";
            case 226: //赤道几内亚
                return "GQ";
            case 232: //厄立特里亚
                return "ER";
            case 233: //爱沙尼亚
                return "EE";
            case 231: //埃塞俄比亚
                return "ET";
            case 238: //福克兰群岛
                return "FK";
            case 234: //法罗群岛
                return "FO";
            case 242: //斐济
                return "FJ";
            case 246: //芬兰
                return "FI";
            case 250: //法国
                return "FR";
            case 249: //法国
                return "FX";
            case 254: //法属圭亚那
                return "GF";
            case 258: //法属波利尼西亚
                return "PF";
            case 260: //法属南部领地
                return "TF";
            case 266: //加蓬
                return "GA";
            case 270: //冈比亚
                return "GM";
            case 275: //巴勒斯坦
                return "PS";
            case 268: //格鲁吉亚
                return "GE";
            case 276: //德国
                return "DE";
            case 288: //加纳
                return "GH";
            case 292: //直布罗陀
                return "GI";
            case 300: //希腊
                return "GR";
            case 304: //格陵兰
                return "GL";
            case 308: //格林纳达
                return "GD";
            case 312: //瓜德罗普
                return "GP";
            case 316: //关岛
                return "GU";
            case 320: //危地马拉
                return "GT";
            case 831: //根西
                return "GG";
            case 324: //几内亚
                return "GN";
            case 624: //几内亚比绍
                return "GW";
            case 328: //圭亚那
                return "GY";
            case 332: //海地
                return "HT";
            case 334: //赫德岛和麦克唐纳群岛
                return "HM";
            case 336: //梵蒂冈
                return "VA";
            case 340: //洪都拉斯
                return "HN";
            case 344: //香港
                return "HK";
            case 348: //匈牙利
                return "HU";
            case 352: //冰岛
                return "IS";
            case 356: //印度
                return "IN";
            case 360: //印尼
                return "ID";
            case 364: //伊朗
                return "IR";
            case 368: //伊拉克
                return "IQ";
            case 372: //爱尔兰
                return "IE";
            case 833: //马恩岛
                return "IM";
            case 376: //以色列
                return "IL";
            case 380: //意大利
                return "IT";
            case 388: //牙买加
                return "JM";
            case 392: //日本
                return "JP";
            case 832: //泽西
                return "JE";
            case 400: //约旦
                return "JO";
            case 398: //哈萨克斯坦
                return "KZ";
            case 404: //肯尼亚
                return "KE";
            case 296: //基里巴斯
                return "KI";
            case 408: //朝鲜
                return "KP";
            case 410: //韩国
                return "KR";
            case 414: //科威特
                return "KW";
            case 417: //吉尔吉斯斯坦
                return "KG";
            case 418: //老挝
                return "LA";
            case 428: //拉脱维亚
                return "LV";
            case 422: //黎巴嫩
                return "LB";
            case 426: //莱索托
                return "LS";
            case 430: //利比里亚
                return "LR";
            case 434: //利比亚
                return "LY";
            case 438: //列支敦士登
                return "LI";
            case 440: //立陶宛
                return "LT";
            case 442: //卢森堡
                return "LU";
            case 446: //澳门
                return "MO";
            case 807: //马其顿
                return "MK";
            case 450: //马达加斯加
                return "MG";
            case 454: //马拉维
                return "MW";
            case 458: //马来西亚
                return "MY";
            case 462: //马尔代夫
                return "MV";
            case 466: //马里
                return "ML";
            case 470: //马耳他
                return "MT";
            case 584: //马绍尔群岛
                return "MH";
            case 474: //马提尼克
                return "MQ";
            case 478: //毛里塔尼亚
                return "MR";
            case 480: //毛里求斯
                return "MU";
            case 175: //马约特
                return "YT";
            case 484: //墨西哥
                return "MX";
            case 583: //密克罗尼西亚联邦
                return "FM";
            case 498: //摩尔多瓦
                return "MD";
            case 492: //摩纳哥
                return "MC";
            case 496: //蒙古国
                return "MN";
            case 499: //黑山
                return "ME";
            case 500: //蒙特塞拉特
                return "MS";
            case 504: //摩洛哥
                return "MA";
            case 508: //莫桑比克
                return "MZ";
            case 516: //纳米比亚
                return "NA";
            case 520: //瑙鲁
                return "NR";
            case 524: //尼泊尔
                return "NP";
            case 528: //荷兰
                return "NL";
            case 540: //新喀里多尼亚
                return "NC";
            case 554: //新西兰
                return "NZ";
            case 558: //尼加拉瓜
                return "NI";
            case 562: //尼日尔
                return "NE";
            case 566: //尼日利亚
                return "NG";
            case 570: //纽埃
                return "NU";
            case 574: //诺福克岛
                return "NF";
            case 580: //北马里亚纳群岛
                return "MP";
            case 578: //挪威
                return "NO";
            case 512: //阿曼
                return "OM";
            case 586: //巴基斯坦
                return "PK";
            case 585: //帕劳
                return "PW";
            case 591: //巴拿马
                return "PA";
            case 598: //巴布亚新几内亚
                return "PG";
            case 600: //巴拉圭
                return "PY";
            case 604: //秘鲁
                return "PE";
            case 608: //菲律宾
                return "PH";
            case 612: //皮特凯恩群岛
                return "PN";
            case 616: //波兰
                return "PL";
            case 620: //葡萄牙
                return "PT";
            case 630: //波多黎各
                return "PR";
            case 634: //卡塔尔
                return "QA";
            case 638: //留尼汪
                return "RE";
            case 642: //罗马尼亚
                return "RO";
            case 158: //中华民国
                return "TW";
            case 643: //俄罗斯
                return "RU";
            case 646: //卢旺达
                return "RW";
            case 652: //圣巴泰勒米
                return "BL";
            case 654: //圣赫勒拿
                return "SH";
            case 659: //圣基茨和尼维斯
                return "KN";
            case 662: //圣卢西亚
                return "LC";
            case 663: //圣马丁行政区
                return "MF";
            case 670: //圣文森特和格林纳丁斯
                return "VC";
            case 882: //萨摩亚
                return "WS";
            case 674: //圣马力诺
                return "SM";
            case 678: //圣多美和普林西比
                return "ST";
            case 682: //沙特阿拉伯
                return "SA";
            case 686: //塞内加尔
                return "SN";
            case 688: //塞尔维亚
                return "RS";
            case 690: //塞舌尔
                return "SC";
            case 694: //塞拉利昂
                return "SL";
            case 702: //新加坡
                return "SG";
            case 534: //荷属圣马丁
                return "SX";
            case 703: //斯洛伐克
                return "SK";
            case 705: //斯洛文尼亚
                return "SI";
            case 90: //所罗门群岛
                return "SB";
            case 706: //索马里
                return "SO";
            case 710: //南非
                return "ZA";
            case 239: //南乔治亚和南桑威奇群岛
                return "GS";
            case 728: //南苏丹
                return "SS";
            case 724: //西班牙
                return "ES";
            case 144: //斯里兰卡
                return "LK";
            case 729: //苏丹
                return "SD";
            case 740: //苏里南
                return "SR";
            case 666: //圣皮埃尔和密克隆
                return "PM";
            case 748: //斯威士兰
                return "SZ";
            case 752: //瑞典
                return "SE";
            case 756: //瑞士
                return "CH";
            case 760: //叙利亚
                return "SY";
            case 762: //塔吉克斯坦
                return "TJ";
            case 834: //坦桑尼亚
                return "TZ";
            case 764: //泰国
                return "TH";
            case 626: //东帝汶
                return "TL";
            case 768: //多哥
                return "TG";
            case 772: //托克劳
                return "TK";
            case 776: //汤加
                return "TO";
            case 780: //特立尼达
                return "TT";
            case 788: //突尼斯
                return "TN";
            case 792: //土耳其
                return "TR";
            case 795: //土库曼斯坦
                return "TM";
            case 796: //特克斯和凯科斯群岛
                return "TC";
            case 798: //图瓦卢
                return "TV";
            case 800: //乌干达
                return "UG";
            case 804: //乌克兰
                return "UA";
            case 784: //阿联酋
                return "AE";
            case 826: //英国
                return "GB";
            case 840: //美国
                return "US";
            case 581: //美国本土外小岛屿
                return "UM";
            case 858: //乌拉圭
                return "UY";
            case 860: //乌兹别克斯坦
                return "UZ";
            case 548: //瓦努阿图
                return "VU";
            case 862: //委内瑞拉
                return "VE";
            case 704: //越南
                return "VN";
            case 850: //美属维京群岛
                return "VI";
            case 876: //瓦利斯和富图纳
                return "WF";
            case 732: //撒拉威阿拉伯民主共和国
                return "EH";
            case 887: //也门
                return "YE";
            case 894: //赞比亚
                return "ZM";
            case 716: //津巴布韦
                return "ZW";
            default:
                return "CN";
        }
    }
}
