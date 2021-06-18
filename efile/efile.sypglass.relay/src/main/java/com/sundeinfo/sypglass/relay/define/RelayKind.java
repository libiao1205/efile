package com.sundeinfo.sypglass.relay.define;

public enum RelayKind {

    RELAY_516(516,"516","20","SYP-加工销售合同评审(new)"),
    RELAY_510(510,"510","252","SYP-浮法销售合同会签流程"),

    RELAY_244(244,"244","133","syp-加工基地原片玻璃采购流程"),
    RELAY_64(64,"64","26","耀皮-海运价格审批"),

    RELAY_84(84,"84","38","耀皮-集中采购(年度)"),
    RELAY_349(349,"349","38","耀皮-集中采购(月度)"),
    RELAY_455(455,"455","145","耀皮-子公司技措项目合同会签流程"),
    RELAY_206(206,"206","109","耀皮总部-物资及服务采购流程"),
    RELAY_479(479,"479","145","汽玻项目-采购合同会签流程"),

    RELAY_507(507,"507","251","耀皮-加工基地原片玻璃集中采购(月度)审批"),
    RELAY_210(210,"210","109","江门工玻-采购流程(新)"),
    RELAY_220(220,"220","109","常熟基地-物资采购流程"),
    RELAY_302(302,"302","89","重庆工玻-合同会签流程"),
    RELAY_524(524,"524","145","天津耀皮-TSYP1冷修项目合同会签流程"),
    RELAY_276(276,"276","145","常熟基地-CS3冷修项目合同会签流程"),
    RELAY_449(449,"449","145","常熟基地-CS1冷修项目合同会签流程"),

    RELAY_35(35,"35","2","耀皮-管理规范与程序审批流程");

    private final int id;

    private final String value;

    private final String form;

    private final String description;

    RelayKind(int id,String value, String form, String description) {
        this.id = id;
        this.value = value;
        this.form = form;
        this.description = description;
    }

    public int id() {
        return this.id;
    }

    public String value() {
        return this.value;
    }

    public String form() {
        return this.form;
    }

    public String description() {
        return this.description;
    }


}
