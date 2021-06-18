<template>
    <div class="preview-database-container">
        <el-dialog
            :title="title"
            :visible.sync="visible"
            @open="onOpen"
            :fullscreen="isFullScreen"
            :close-on-click-modal="false"
            :show-close="false"
            custom-class="custom-dialog"
            width="1200px"
            top="7vh"
        >
            <div slot="title" class="custom-title-container">
                <span></span>
                <span class="custom-title"></span>
                <span class="custom-icons">
                    <i v-if="isFullScreen" @click="handleChangeMaxMinDialog">
                        <svg t="1614587768535" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5438" width="200" height="200">
                            <path d="M449.92 543.36C449.92 520.96 431.36 512 410.24 512L97.92 512c-17.92 0-32 14.08-32 32 0 17.92 14.08 32 32 32l242.56 0-267.52 267.52c-12.16 12.16-12.16 31.36 0 43.52 12.16 12.16 31.36 12.16 43.52 0l269.44-269.44 0 246.4c0 17.92 14.08 32 32 32 17.92 0 32-14.08 32-32l0-320M865.92 384 622.72 384l267.52-267.52c12.16-12.16 12.16-31.36 0-43.52-12.16-12.16-31.36-12.16-43.52 0L577.92 342.4 577.92 96C577.92 78.08 563.2 64 545.92 64c-17.92 0-32 14.08-32 32l0 320c0 0.64 0.64 1.28 0.64 1.92 0 8.32 2.56 16.64 8.96 22.4C531.2 448.64 542.72 451.2 552.96 448l312.32 0c17.92 0 32-14.08 32-32C897.92 398.08 883.2 384 865.92 384z" p-id="5439" fill="#ffffff"></path>
                        </svg>
                    </i>
                    <i v-else @click="handleChangeMaxMinDialog">
                        <svg t="1614587805849" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5979" width="200" height="200">
                            <path d="M444.3 539.9L202 782.2 199.8 563c0-16.5-13.5-30-30-30s-30 13.5-30 30l2.2 285.1c0 8.8 3.8 16.7 9.8 22.2 5.5 6 13.4 9.8 22.2 9.8h295.6c16.5 0 30-13.5 30-30s-13.5-30-30-30H248.9l237.8-237.8c11.7-11.7 11.7-30.8 0-42.4-11.6-11.6-30.7-11.6-42.4 0zM578.1 488l242.3-242.3 2.2 219.2c0 16.5 13.5 30 30 30s30-13.5 30-30l-2.2-285.1c0-8.8-3.8-16.7-9.8-22.2-5.5-6-13.4-9.8-22.2-9.8H552.8c-16.5 0-30 13.5-30 30s13.5 30 30 30h220.7L535.7 445.6c-11.7 11.7-11.7 30.8 0 42.4 11.7 11.7 30.8 11.7 42.4 0z" p-id="5980" fill="#ffffff"></path>
                        </svg>
                    </i>
                    <!-- <i class="el-icon-close" v-if="isFullScreen" @click="handleChangeMaxMinDialog"></i>
                    <i class="el-icon-close" v-else @click="handleChangeMaxMinDialog"></i> -->
                    <i class="el-icon-close" @click="handleCloseDialog"></i>
                </span>
            </div>
            <div class="table-content">
                <el-row>
                    <el-col :span="6">
                        <el-table :data="tables" :height="tableHeight" highlight-current-row @current-change="handleCurrentChange">
                            <el-table-column
                                prop="tablename"
                                :label="dbName"
                            ></el-table-column>
                        </el-table>
                    </el-col>
                    <el-col :span="18">
                        <el-table :data="columns" width="100%" border ref="dataCol" :height="tableHeight" v-loading="loading">
                            <template v-for="(item, index) in columnProps">
                                <el-table-column
                                    :formatter="formatterCol"
                                    :key="index"
                                    :prop="item"
                                    :label="item"
                                    width="160"
                                ></el-table-column>
                            </template>
                        </el-table>
                        <div style="padding-top: 15px;text-align:center;">
                            <el-pagination
                                background
                                @current-change="selectPage"
                                @size-change="handleSizeChange"
                                layout="total, sizes, prev,pager,next,jumper"
                                :current-page="curPage"
                                :total="total"
                                :page-sizes="[10, 20, 30, 40, 50]"
                                :page-size="pageSize">
                            </el-pagination>
                        </div>
                    </el-col>
                </el-row>
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: 'previewDatabase',
    data() {
        return {
            title: '',
            visible: false,
            dbId: null,
            tables: [],
            columns: [],
            columnProps: [],
            dbName: '',
            currentRow: null,
            itemColumns: [],
            isFullScreen: false,
            tableHeight: '600px',
            loading: false,
            pageSize:10,
            curPage:1,
            total:0,
            tableName:''
        };
    },
    mounted() {
        this.tableHeight = document.documentElement.clientHeight - 240;
    },
    methods: {
        onOpen() {
            this.$axios({
                url: `external/dbById/${this.dbId}`,
                method: 'get',
            }).then((res) => {
                if (res.c === 0) {
                    this.dbName = res.ctt.aliasName == '' ? res.ctt.dbName : res.ctt.aliasName;
                    this.getTables();
                }else{
                    this.dbName = '该数据不存在';
                }
            });
        },
        getTables() {
            if (!this.dbId) {
                return;
            }
            this.$axios({
                method: 'get',
                url: `/external/dbTableById/${this.dbId}`,
            }).then((res) => {
                // console.log(res);
                if (res.c === 0) {
                    this.tables = res.ctt;
                }
            }).catch((err) => {
                this.$message.error(`获取数据失败，失败码：${err.response.status}`);
            });
        },
        getColumns(col) {
            this.loading = true;
            this.columns = [];
            this.columnProps = [];
            this.$axios({
                method: 'get',
                url: `/external/findTableContent/${this.dbId}/${col}/${this.curPage}-${this.pageSize}`,
            }).then((res) => {
                this.total = 0;
                if (res.c === 0 && res.ctt !== null) {
                    //this.tableHeight = this.isFullScreen ? 'calc(100vh - 110px)' : '800px';
                    const tbCol = this.$refs['dataCol'];
                    // console.log(tbCol);
                    // console.log(tbCol.bodyHeight.height);
                    //tbCol.bodyHeight.height = this.isFullScreen ? `calc(100vh - 160px)` : `${800 - 50}px`;
                    // console.log(tbCol.bodyHeight.height);
                    let result = JSON.parse(JSON.parse(res.ctt).ctt).rows;
                    this.total = JSON.parse(JSON.parse(res.ctt).ctt).count;
                    if(this.total > 0){
                        const itemColumns = this.tables.find((i) => i.tablename === col).columns;
                        this.itemColumns = itemColumns;
                        this.columns = result;
                        if(result.length > 0){
                            this.columnProps = Object.keys(result[0]);
                        }
                    }
                }
                this.loading = false;
            }).catch((err) => {
                this.loading = false;
                this.$message.error(`获取数据失败，失败码：${err.msg}`);
            });
        },
        handleCurrentChange(item) {
            // console.log(item);
            this.tableName = item.tablename;
            if (this.currentRow !== this.tableName) {
                this.currentRow === this.tableName;
                this.getColumns(this.tableName);
            }
        },
        handleChangeMaxMinDialog() {
            this.isFullScreen = !this.isFullScreen;
            if (this.isFullScreen) {
                this.tableHeight = 'calc(100vh - 110px)';
            } else {
                this.tableHeight = '800px';
            }
        },
        handleCloseDialog() {
            this.visible = false;
            this.isFullScreen = false;
        },
        formatDateTime(val) {
            const date = new Date(val);
            const year = date.getFullYear();
            const month = date.getMonth() + 1;
            const day = date.getDate();
            const h = date.getHours() < 10 ? `0${date.getHours()}` : date.getHours();
            const m = date.getMinutes() < 10 ? `0${date.getMinutes()}` : date.getMinutes();
            const s = date.getSeconds() < 10 ? `0${date.getSeconds()}` : date.getSeconds();
            const datetime = `${year}/${month}/${day} ${h}:${m}:${s}`;
            if (datetime === NaN) {
                return val;
            }
            return datetime;
        },
        formatterCol(row, column, cellValue, index) {
            const itemColumns = JSON.parse(JSON.stringify(this.itemColumns));
            const dateCols = itemColumns.filter((i) => (i.datatype === 'datetime') || (i.datatype === 'date'));
            const dateColsTxt = dateCols.map((i) => i.columnname);
            if (dateColsTxt.includes(column.property)) {
                cellValue = this.formatDateTime(row[column.property]);
            }
            if (typeof row[column.property] === 'boolean') {
                cellValue = row[column.property] ? 'true' : 'false';
            }
            return cellValue;
        },
        selectPage(val){
            this.curPage = val
            if(this.tableName){
                this.getColumns(this.tableName);
            }
        },
        handleSizeChange(val) {
            this.pageSize = val;
            if(this.tableName){
                this.getColumns(this.tableName);
            }
        },
    },
}
</script>

<style lang="scss">
    .preview-database-container {
        .table-content {
            // height: 800px;
            .el-row, .el-col {
                height: 100%;
            }
        }
    }
    .custom-title-container {
        display: flex;
        flex-direction: row;
    }
    .custom-title {
        -webkit-flex: 1;
        flex: 1;
        color: white;
        line-height: 24px;
        font-size: 18px;
        font-weight: bold;
    }
    .custom-icons {
        color: white;
        text-align: right;
    }
    .custom-icons i {
        cursor: pointer;
    }
    .custom-dialog {
        .el-dialog__body {
            padding: 5px;
            // height: calc(100vh - 60px);
        }
    }
</style>
