const dataTypes = [
    {
        label: '字符串',
        value: 1,
    },
    {
        label: '大文本',
        value: 2,
    },
    {
        label: '整数',
        value: 3,
    },
    {
        label: '长整数',
        value: 4,
    },
    {
        label: '浮点数',
        value: 5,
    },
    {
        label: '布尔值',
        value: 6,
    },
    {
        label: '日期',
        value: 7,
    },
];

function formatDataType(val) {
    let result;
    switch (val) {
        case '字符串':
        case '大文本':
        case '日期':
            result = '';
            break;
        case '布尔值':
            result = false;
            break;
        case '整数':
        case '长整数':
        case '浮点数':
            result = 0;
            break;
        default:
            result = '';
            break;
    }
    return result;
}

function dataTypeToText(num) {
    let item = dataTypes.find((i) => i.value === num);
    if (item) {
        return item.label;
    }
    return '';
}

function checkTime(i) {
    if (i < 10) {
        i = '0' + i;
    }
    return i;
}

function generateNewName(name, i) {
    if (!i) {
        i = 1;
    }
    const preName = name.split('.').slice(0, -1).join('.');
    const suffix = name.substring(name.lastIndexOf('.') + 1);
    return `${preName}(${i}).${suffix}`;
}

function formatDateTime(val, type = '/') {
    let first = '/';
    let second = '/';
    let third = '';
    const date = new Date(val);
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const h = date.getHours() < 10 ? `0${date.getHours()}` : date.getHours();
    const m = date.getMinutes() < 10 ? `0${date.getMinutes()}` : date.getMinutes();
    const s = date.getSeconds() < 10 ? `0${date.getSeconds()}` : date.getSeconds();
    let hms = ` ${h}:${m}:${s}`;
    if (type === '年月日') {
        first = '年';
        second = '月';
        third = '日';
        hms = '';
    }
    const datetime = `${year}${first}${month}${second}${day}${third}${hms}`;
    if (datetime === NaN) {
        return val;
    }
    return datetime;
}

module.exports = {
    dataTypes,
    formatDataType,
    checkTime,
    dataTypeToText,
    generateNewName,
    formatDateTime,
};
