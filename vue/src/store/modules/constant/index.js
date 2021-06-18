function  getDisplayName(dataList, value, defaultValue) {
    let data = dataList.find(c => c.code === value);
    if (data)
        return data.value;
    else
        return defaultValue
}

const state = {
    clusterList:
        [
            {code:"keyword",value:"关键词"},
            {code:"abstract",value:"摘要"},
            {code:"author",value:"作者"},
            {code:"authorUnit",value:"作者单位"},
            {code:"projectName",value:"项目名称"},
            {code:"compileTime",value:"编制时间"},
            {code:"reportType",value:"报告类型"},
            {code:"publicScope",value:"公开范围"},
            {code:"SourceTitle",value:"来源刊名"},
            {code:"postedTime",value:"发表时间"},
            {code:"caseSource",value:"案例源"},
            {code:"informationSource",value:"资讯来源"},
            {code:"releaseTime",value:"发布时间"},
            {code:"WhetherSourceMedia",value:"是否来源于重要媒体"},
            {code:"docType",value:"附件类型"}
        ]
};

const getters = {
    getClusterName:(state) => (value) =>{
        return getDisplayName(state.clusterList,value,'空')
    },
};
export default {
    namespaced: true,
    state,
    getters
}
