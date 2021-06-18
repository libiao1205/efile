<template>
    <div class="edit-matadata-container">
        <el-dialog :title="dialogTitle" :visible.sync="visible" :close-on-click-modal="false" width="700px" @open="onOpen" @close="onClose">
            <el-form ref="form" :model="form" :rules="rules" label-width="80px" :label-position="'left'">
                <el-row :gutter="40">
                    <el-col :span="12">
                        <el-form-item label="源数据id" prop="id">
                            <el-input v-model="form.id" disabled></el-input>
                        </el-form-item>
                        <el-form-item label="文档类型" prop="docType">
                            <el-select v-model="form.docType" placeholder="请选择文档类型" @change="handleChangeDocType">
                                <el-option
                                    v-for="(item, index) in docTypes"
                                    :key="index"
                                    :label="item.comment"
                                    :value="item.value"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="字段编码" prop="code">
                            <el-input v-model="form.code"></el-input>
                        </el-form-item>
                        <el-form-item label="字段名称" prop="name">
                            <el-input v-model="form.name"></el-input>
                        </el-form-item>
                        <el-form-item label="排序" prop="seq">
                            <el-input v-model="form.seq" type="number"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="数据类型" prop="dataType">
                            <!-- <el-input v-model="form.dataType"></el-input> -->
                            <el-select v-model="form.dataType" placeholder="请选择数据类型" @change="handleChangeDataType">
                                <el-option
                                    v-for="(item, index) in dataTypes"
                                    :key="index"
                                    :label="item.label"
                                    :value="item.value"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="长度" prop="size">
                            <el-input v-model="form.size" type="number"></el-input>
                        </el-form-item>
                        <el-form-item label="非空" prop="notnull">
                            <!-- <el-input v-model="form.notnull"></el-input> -->
                            <el-switch
                                v-model="form.notnull"
                                active-text="是"
                                inactive-text="否"
                            ></el-switch>
                        </el-form-item>
                        <el-form-item label="solr字段" prop="solrId">
                            <!-- <el-input v-model="form.solrField"></el-input> -->
                            <!-- <el-button @click="showSolrFieldDialog">编辑</el-button> -->
                            <el-input v-model="form.solrName" readonly>
                                <el-button slot="append" icon="el-icon-more" @click="showSolrFieldDialog"></el-button>
                            </el-input>
                            <div class="el-form-item__error custom" v-if="form.solrId === ''">请选择solr字段</div>
                        </el-form-item>
                        <el-form-item label="说明" prop="comment">
                            <el-input type="textarea" v-model="form.comment"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <!-- <el-row>
                    <el-form-item label="说明" prop="desc">
                        <el-input type="textarea" v-model="form.desc"></el-input>
                    </el-form-item>
                </el-row> -->
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" icon="iconfont icon-add-new" @click="dialogOnSubmit">
                    <span style="padding-left: 10px;"><b>确定</b></span>
                </el-button>
                <el-button icon="iconfont icon-jieshu" @click.native="dialogOnClose">
                    <span style="padding-left: 10px;"><b>关闭</b></span>
                </el-button>
            </div>
            <solr-field ref="solrField"></solr-field>
        </el-dialog>
    </div>
</template>

<script>
import {dataTypes} from '../../../util/initData';
import Utils from '../../../util/utils';
import SolrField from './SolrField';
export default {
    name: 'EditMetaData',
    components: {
        SolrField,
    },
    data() {
        return {
            visible: false,
            dialogType: 'create',
            rules: {
                docType: [
                    {
                        required: true,
                        message: '请选择文档类型',
                        trigger: 'change',
                    },
                ],
                code: [
                    {
                        required: true,
                        message: '请输入字段编码',
                        trigger: 'blur',
                    },
                ],
                name: [
                    {
                        required: true,
                        message: '请输入字段名称',
                        trigger: 'blur',
                    },
                ],
                seq: [
                    {
                        required: true,
                        message: '请输入排序',
                        trigger: 'blur',
                    },
                ],
                dataType: [
                    {
                        required: true,
                        message: '请输入数据类型',
                        trigger: 'blur',
                    },
                ],
                size: [
                    {
                        required: true,
                        message: '请输入长度',
                    },
                    {validator:true,
                        validator:(rule,value,callback)=>{
                                                if(value>4000){
                                                    return callback(new Error('长度不能大于4000'));
                                                }else{
                                                    return callback()
                                                }
                                            },
                        trigger: 'blur'},
                ],
            },
            form: {
                id: '',
                docType: '',
                code: '',
                name: '',
                seq: '',
                dataType: '',
                size: '',
                notnull: false,
                solrId: '',
                solrName: '',
                comment: '',
            },
            docTypes: [],
            dataTypes: [],
        };
    },
    computed: {
        dialogTitle() {
            if (this.dialogType === 'edit') {
                return '修改元数据';
            }
            return '新增元数据';
        }
    },
    mounted() {
        // console.log(dataTypes);
        this.dataTypes = dataTypes;
        Utils.$on('selectedSolr', (val) => {
            // console.log(val);
            this.form.solrId = val.id;
            this.form.solrName = val.name;
        });
    },
    methods: {
        onOpen() {
            // console.log(this.docTypes);
        },
        onClose() {
            this.initForm();
            this.$refs['form'].resetFields();
        },
        initForm() {
            this.form  = {
                id: '',
                docType: '',
                code: '',
                name: '',
                seq: '',
                dataType: '',
                size: '',
                notnull: false,
                solrId: '',
                solrName: '',
                comment: '',
            };
        },
        dialogOnClose() {
            this.visible = false;
            this.initForm();
            this.$refs['form'].resetFields();
        },
        dialogOnSubmit() {
            this.$refs['form'].validate((valid) => {
                if (valid && (this.form.solrId !== '')) {
                    // return;
                    if (this.dialogType === 'create') {
                        const {id, docType, notnull, solrName, solrId, ...formItem} = this.form;
                        const data = {
                            ...formItem,
                            docType: parseInt(docType),
                            notnull: notnull ? 1 : 0,
                            status: 1,
                            solrId,
                        };
                        this.$axios({
                            method: 'post',
                            url: `/external/metadata`,
                            data,
                        }).then((res) => {
                            this.$message.success('新建成功');
                            this.dialogOnClose();
                            Utils.$emit('refreshMetadata');
                        }).catch((err) => {
                            this.$message.error('新建失败');
                        });
                    }
                    if (this.dialogType === 'edit') {
                        const data = {
                            ...this.form,
                            notnull: this.form.notnull ? 1 : 0,
                            docType: parseInt(this.form.docType),
                        };
                        this.$axios({
                            method: 'put',
                            url: `/external/metadata`,
                            data,
                        }).then((res) => {
                            if (res.c === 0) {
                                this.$message.success('修改成功');
                                this.dialogOnClose();
                                Utils.$emit('refreshMetadata');
                            }
                        }).catch((err) => {
                            this.$message.error('修改失败');
                        });
                    }
                }
            });
        },
        showSolrFieldDialog() {
            if (this.form.docType === '') {
                this.$message.warning('请选择文档类型');
                return;
            }
            this.$refs['solrField'].visible = true;
            this.$refs['solrField'].docType = this.form.docType;
        },
        handleChangeDocType(val) {
            // console.log(val);
        },
        handleChangeDataType(val) {},
    },
}
</script>

<style lang="scss">
// .edit-matadata-container {
//     .el-form-item__error.custom {
//         color: #c0c4cc;
//     }
// }
</style>
