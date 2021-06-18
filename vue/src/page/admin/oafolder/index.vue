<template>
    <el-container>
        <el-header height="79" class="pd0">
            <div class="app-title fxb bb1 pd10">
                <div class="fxr fnt-s20 pd10" >
                    <h1><app-svg iconClass="OA"></app-svg>  OA导入附件目录设定</h1>
                </div>
            </div>
            <div class="fxb bb1" >
                <div class="fxr pd5">
                    <el-button type="primary" size="mini" icon="iconfont icon-add" plain @click.native="$refs.setting.handleAddSetting">  新增配置</el-button>
                </div>
            </div>
        </el-header>

        <el-main class="sys-content pd0">
            <el-scrollbar >
                <el-table ref="table" v-loading="tableFolderSetting.loading" :data="tableFolderSetting.body" highlight-current-row
                          :cell-style="{padding:'5px'}" class="custom-table-cell"
                          :header-cell-style="{padding: '2px'}" :height="tableFolderSetting.height">
                    <el-table-column type="index" label="序号"  align="center" width="64px"></el-table-column>
                    <el-table-column prop="workflowname" label="流程名称" width="350px" ></el-table-column>
                    <el-table-column label="已定义流程属性详细"  width="300px">
                        <template slot-scope="scope">
                            <div >
                                <span style="font-weight: bold">
                                    {{scope.row.firstdictname}} - {{scope.row.seconddictname}} - {{scope.row.thirddictcode}}
                                </span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column label="已指定目录" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <div >
                                <span style="font-weight: bold">
                                    {{scope.row.fullname}}
                                </span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" width="150px">
                        <template slot-scope="scope">
                            <div>
                                <el-tooltip class="item" effect="dark" content="编辑配置目录" placement="bottom">
                                    <el-button class="btnEdit" type="text" size="mini"
                                               icon="iconfont icon-edit-file"
                                               @click.native="$refs.setting.handleEdit(scope.$index, scope.row)"
                                               circle></el-button>
                                </el-tooltip>
                                <el-tooltip class="item" effect="dark" content="删除配置" placement="bottom">
                                    <el-button class="btnDel" type="text" size="mini"
                                               icon="iconfont icon-trash"
                                               @click.native="$refs.setting.handleDeleteSettingSubmit(scope.$index, scope.row)"
                                               circle></el-button>
                                </el-tooltip>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
            </el-scrollbar>
        </el-main>
        <edit-setting ref="setting" :add-job="getData" :edit-job="getData"></edit-setting>
    </el-container>
</template>

<script>
    import EditSetting from "./EditSetting";
    export default {
        name: 'log',
        components: {EditSetting},
        data() {
            return {
                searchForm: {
                    time: '',
                    type: '',
                    text: ''
                },
                tableFolderSetting: {
                    height: 0,
                    loading: false,
                    body: []
                },


            }
        },
        mounted() {
            this.getData()
            this.tableHeight()
        },
        methods: {
            //表格高度计算
            tableHeight(){
                this.$nextTick(function () {
                    this.tableFolderSetting.height = window.innerHeight - this.$refs.table.$el.offsetTop - 200;
                    let self = this;
                    window.onresize = function() {// 监听窗口大小变化
                        self.tableFolderSetting.height = window.innerHeight - self.$refs.table.$el.offsetTop - 200
                    }
                })
            },
            // 获取table数据
            getData() {
                this.tableFolderSetting.loading = true
                this.$axios({
                    url: `/relay/settings`,
                    method: 'get'
                }).then(res => {
                    this.tableFolderSetting.body = res.ctt
                    this.tableFolderSetting.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
        }
    }
</script>

<style scoped>

</style>
