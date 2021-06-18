<template>
    <div class="upload-resource-container">
        <el-dialog :title="dialogTitle" :visible.sync="visible" width="800px" :close-on-click-modal="false" @open="handleOnOpen" @close="handleOnClose">
            <el-form ref="form" :model="form" label-width="90px" :label-position="'left'">
                <el-row :gutter="40">
                    <el-col :span="12" style="margin-bottom: 10px;">
                        <el-form-item label="文档类型" prop="docType" :rules="rules.docType">
                            <el-select v-model="form.docType" placeholder="请选择文档类型" @change="handleChangeDocType">
                                <el-option v-for="(item, index) in docTypes" :key="index" :label="item.comment" :value="item.value"></el-option>
                            </el-select>
                        </el-form-item>
                        <div style="padding-bottom: 10px;">选择上传文件</div>
                        <el-upload
                            class="upload-demo"
                            drag
                            multiple
                            ref="uploadFile"
                            :auto-upload="false"
                            :headers="token"
                            :file-list="formFile.fileList"
                            :action="formFile.action"
                            :on-change="onFileChange"
                            :on-remove="onFileRemove"
                            :onSuccess="onUploadSuccess"
                            :onError="onUploadError"
                        >
                            <i class="el-icon-upload"></i>
                            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                            <div class="el-upload__tip" slot="tip">文件大小不能超过{{this.upload.size}}MB</div>
                        </el-upload>
                        <el-form-item label="引用数据源" prop="quoteData" style="margin-top: 20px;">
                            <el-input v-model="form.quoteData.aliasName" readonly>
                                <el-button slot="append" icon="el-icon-more" @click="handleClickQuoteData"></el-button>
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <template v-for="(items, index) in formFields">
                            <el-form-item :key="index" :label="items.name" :prop="items.code" :rules="items.rules">
                                <el-input v-if="items.types === '字符串'" v-model="items.value" @input="handleSetVal(items.code, items.value)"></el-input>
                                <el-input v-else-if="items.types === '大文本'" type="textarea" v-model="items.value" placeholder="不要超过200字。" @input="handleSetVal(items.code, items.value)"></el-input>
                                <el-date-picker v-else-if="items.types === '日期'" type="date" placeholder="选择日期" v-model="items.value" style="width: 100%;" @change="handleSetVal(items.code, items.value)"></el-date-picker>
                                <el-switch
                                    v-else-if="items.types === '布尔值'"
                                    v-model="items.value"
                                    active-text="是"
                                    inactive-text="否"
                                ></el-switch>
                                <div class="el-form-item__error" v-if="checkItemsRule(items)">此项为必填项</div>
                                <div class="el-form-item__error" v-if="items.value.length > items.size">此项长度不能超过{{items.size}}个字符</div>
                            </el-form-item>
                        </template>
                    </el-col>
                </el-row>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button type="primary" icon="iconfont icon-add-new" @click="dialogOnSubmit">
                    <span style="padding-left: 10px;"><b>确定</b></span>
                </el-button>
                <el-button icon="iconfont icon-jieshu" @click.native="dialogOnClose">
                    <span style="padding-left: 10px;"><b>关闭</b></span>
                </el-button>
            </div>
        </el-dialog>

        <el-dialog title="选择数据源" :visible.sync="dataSourceVisible" width="400px">
            <el-table :data="dataSourceList" highlight-current-row @current-change="handleCurrentChange">
                <el-table-column prop="aliasName" :formatter="nameFormat" label="名称"></el-table-column>
            </el-table>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" icon="iconfont icon-add-new" @click="quoteDataDialogOnSubmit">
                    <span style="padding-left: 10px;"><b>确定</b></span>
                </el-button>
                <el-button icon="iconfont icon-jieshu" @click.native="quoteDataDialogOnClose">
                    <span style="padding-left: 10px;"><b>关闭</b></span>
                </el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import Utils from '../../util/utils';
import {dataTypes, formatDataType, generateNewName} from '../../util/initData';
export default {
    name: 'uploadResource',
    data() {
        return {
            // title: '文献上传',
            types: 'create',
            visible: false,
            dataSourceVisible: false,
            form: {
                docType: '',
                quoteData: {
                    dbName: '',
                    aliasName: '',
                },
            },
            rules: {
                docType: [
                    {
                        required: true,
                        message: '此项为必填项',
                        trigger: 'blur',
                    },
                ],
            },
            formFields: [],
            docTypes: [],
            dataSourceList: [],
            selectedQuoteData: null,
            folderId: -1,
            bodyFormData: null,
            searchData: null,
            upload: {
                url:this.$upload,
                size: this.$maxFileSize,
            },
            formFile: {
                isNew: false,
                title:'',
                action:"",
                visible: false,
                loading: false,
                data:{
                    id: 0 ,
                    fileName:'',
                    folderId:0,
                    folderName:'',
                    comment:'',
                },
                fileList: [],
                uploadFileList: [],
            },
            paginationData: {
                pageSize: 1000,
                total: 0,
                curPage:1
            },
            editData: {},
            deleteStr: [],
        };
    },
    computed: {
        token() {
            return {Authorization :'Bearer ' + this.$store.state.auth.token}
        },
        dialogTitle() {
            if (this.types === 'edit') {
                return '修改文献';
            }
            return '文献上传';
        },
    },
    mounted() {
        // this.initDocTypes();
    },
    methods: {
        initFormFields(type) {
            this.$axios({
                method: 'get',
                url: `/external/metadata/type/${type}`,
            }).then((res) => {
                if (res.c === 0) {
                    this.formFields = res.ctt.map((item) => {
                        let types = dataTypes.find((i) => i.value === item.dataType).label;
                        this.form[item.code] = formatDataType(types);
                        let result = {
                            ...item,
                            types,
                            value: formatDataType(types),
                            rules: [],
                        };
                        if (result.notnull === 0) {
                            result.rules = [];
                        }
                        return result;
                    });
                    // console.log(JSON.parse(JSON.stringify(this.formFields)));
                }
            }).catch((err) => {
                this.$message.error(`获取数据失败，失败码：${err.response.status}`);
            });
        },
        initForm() {
            this.form = {
                docType: '',
                quoteData: {
                    dbName: '',
                },
            };
        },
        checkItemsRule(items) {
            if (items.notnull === 1) {
                if (items.value === '') {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        },
        handleSetVal(name, value) {
            this.$set(this.form, name, value);
        },
        handleChangeDocType(val) {
            if (val) {
                this.initFormFields(val);
            } else {
                this.initForm();
            }
        },
        handleOnOpen() {
            this.initDocTypes();
            const isNew = (this.types === 'create')  // && (JSON.stringify(this.editData) === '{}');
            if (!isNew) {
                const docType = this.editData.fileMetadataDto[0].docType;
                this.form.docType = String(docType);
                const editData = JSON.parse(JSON.stringify(this.editData));
                const db = editData.fileAccessoryDto.find((i) => i.type === 2);
                if (db) {
                    this.$axios({
                        url: `external/dbById/${db.saveId}`,
                        method: 'get',
                    }).then((res) => {
                        if (res.c === 0) {
                            this.form.quoteData = {
                                aliasName: res.ctt.aliasName,
                                dbName: db.name,
                                dbId: db.saveId,
                            };
                        }
                    });
                }
                const files = editData.fileAccessoryDto.filter((i) => i.type === 1);
                // console.log(files);
                this.formFile.fileList = files.map((i) => {
                    return {
                        name: `${i.name}.${i.suffix}`,
                        id: i.id,
                        url: '',
                    };
                });
                // console.log(editData);
                this.formFields = editData.fileMetadataDto.map((item) => {
                    let types = dataTypes.find((i) => i.value === item.dataType).label;
                    this.form[item.code] = formatDataType(types);
                    let result = {
                        ...item,
                        types,
                        value: formatDataType(types),
                        rules: [],
                        id: item.metadataId,
                    };
                    if(item.dataType === 6){
                        const value = editData.fileMetadataDto.find((i) => i.code === item.code).value === 'true' ? true : false;
                        result.value = value;
                    }else{
                        const value = editData.fileMetadataDto.find((i) => i.code === item.code).value;
                        result.value = value;
                    }

                    if (result.notnull === 0) {
                        result.rules = [];
                    }
                    return result;
                });
                // console.log(this.formFields);
            }
        },
        handleOnClose() {
            this.initForm();
            this.$refs['form'].resetFields();
            this.formFile.fileList = [];
            this.formFile.uploadFileList = [];
            this.formFields = [];
            this.editData = {};
            this.deleteStr = [];
        },
        initDocTypes() {
            this.$axios({
                method: 'get',
                url: `/external/metadataType`,
            }).then((res) => {
                this.docTypes = res.ctt;
            });
        },
        onFileChange(file, fileList) {
            // console.log(file.uid, file.name);
            // console.log(fileList);
            let uploadFiles = this.$refs['uploadFile'].uploadFiles;
            if (!this.checkFileSize(file)) {
                this.$message.error(`上传文件大小不能超过 ${this.upload.size}MB!`);
                this.$refs['uploadFile'].uploadFiles.pop()
                return
            }
            if (uploadFiles.filter((i) => i.name === file.name).length > 1) {
                this.$confirm('存在同名文件，覆盖还是作为新文件上传？', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning',
                    confirmButtonText: '覆盖',
                    cancelButtonText: '新文件',
                    distinguishCancelAndClose: true,
                }).then(() => {
                    uploadFiles = uploadFiles.filter((i) => i.name !== file.name);
                    file.isCover = true;
                    uploadFiles.push(file);
                    this.formFile.fileList = uploadFiles;
                }).catch(() => {
                    file.name = generateNewName(file.name, uploadFiles.length - 1);
                    uploadFiles = uploadFiles.filter((i) => i.name !== file.name);
                    uploadFiles = uploadFiles.filter((i) => i.uid !== file.uid);
                    uploadFiles.push(file);
                    this.formFile.fileList = uploadFiles;
                });
            }
            // this.formFile.uploadFileList = fileList;
        },
        onFileRemove(file, fileList) {
            if (file.id) {
                this.deleteStr.push(file.id);
            }
            // this.formFile.uploadFileList = fileList;
            this.formFile.uploadFileList = this.formFile.uploadFileList.filter((i) => i.uid === file.uid);
        },
        onUploadSuccess(response, file, fileList) {
            this.$message.success('文件上传完成')
            this.uploaded()
            this.formFile.loading = false
            this.formFile.visible = false
            console.log('上传文件', response)
        },
        onUploadError(response, file, fileList) {
            this.$message.error(`上传失败，请重试！`)
            this.uploaded()
            this.formFile.loading = false
            this.formFile.visible = false
            console.log('上传失败')
        },
        checkFileSize(file) {
            return file.size / 1024 / 1024 <= this.upload.size
        },
        handleClickQuoteData() {
            this.dataSourceVisible = true;
            this.initDatabases();
        },
        initDatabases() {
            let url = `/external/dbs/1-10000?name=`;;
            if (this.searchData) {
                url = `/external/dbs/1-10000?name=${this.searchData}`;
            }
            this.$axios({
                method: 'get',
                url,
            }).then((res) => {
                if (res.c === 0) {
                    this.dataSourceList = res.ctt.map((i) => {
                        return {
                            ...i,
                            dbId: i.id,
                        };
                    });
                }
            }).catch((err) => {
                this.$message.error(`获取数据失败，失败码：${err.response.status}`);
            });
        },
        handleCurrentChange(val) {
            this.selectedQuoteData = val;
        },
        quoteDataDialogOnSubmit() {
            if (!this.selectedQuoteData) {
                this.$message.warning('请选择数据库');
                return;
            }
            this.form.quoteData = this.selectedQuoteData;
            this.dataSourceVisible = false;
        },
        quoteDataDialogOnClose() {
            this.dataSourceVisible = false;
        },
        dialogOnSubmit() {
            if (this.folderId === -1) {
                return;
            }
            this.$refs['form'].validate((valid) => {
                if (valid && this.checkValid()) {
                    this.handleSubmitData();
                } else {
                    this.$message.warning('请检查输入的数据');
                }
            });
        },
        checkValid() {
            let notnullItems = this.formFields.filter((i) => i.notnull === 1);
            let notnullItem = notnullItems.find((i) => i.value === '');
            if (notnullItem) {
                return false;
            } else {
                if (this.formFields.find((i) => i.value.length > i.size)) {
                    return false;
                } else {
                    return true;
                }
                // return true;
            }
        },
        handleSubmitData() {
            if (this.types === 'edit') {
                const uploadFiles = this.$refs['uploadFile'].uploadFiles;
                const metadata = this.formFields.map((i) => {
                    return {
                        metadataId: i.id,
                        dataType: i.dataType,
                        code: i.code,
                        name: i.name,
                        seq: i.seq,
                        value: i.value,
                    };
                });
                // const oldFiles = JSON.parse(JSON.stringify(this.editData.fileAccessoryDto.filter((i) => i.type === 1)));
                // console.log(oldFiles);
                const newFiles = uploadFiles.filter((i) => i.status !== 'success');
                const coverFiles = newFiles.filter((i) => i.isCover);
                const files = newFiles.filter((i) => !i.isCover);
                const data = {
                    files,
                    coverFiles,
                    deleteStr: JSON.stringify(this.deleteStr),
                    database: this.form.quoteData.dbId ? this.form.quoteData.dbId : '',
                    metadata: JSON.stringify(metadata),
                };

                // console.log(data);

                const bodyFormData = new FormData();
                bodyFormData.append('metaStr', data.metadata);
                bodyFormData.append('deleteStr', data.deleteStr);
                if (data.database) {
                    bodyFormData.append('database', data.database);
                } else {
                    bodyFormData.append('database', '');
                }
                if (data.files.length > 0) {
                    for (let i = 0; i < data.files.length; i += 1) {
                        let file = data.files[i];
                        if (file) {
                            let nFile = new File([file.raw], file.name, { type: file.raw.type });
                            bodyFormData.append('files', nFile);
                        }
                    }
                }
                if (data.coverFiles.length > 0) {
                    for (let i = 0; i < data.coverFiles.length; i += 1) {
                        let file = data.coverFiles[i];
                        if (file) {
                            let nFile = new File([file.raw], file.name, { type: file.raw.type });
                            bodyFormData.append('coverFiles', nFile);
                        }
                    }
                }

                this.$axios({
                    method: 'post',
                    url: `/user/file/${this.editData.id}/upload/literature`,
                    data: bodyFormData,
                    headers: { "Content-Type": "multipart/form-data" },
                }).then((res) => {
                    if (res.c === 0) {
                        this.$message.success('修改成功');
                        this.handleOnClose();
                        this.visible = false;
                        Utils.$emit('refreshHome');
                    }
                }).catch((err) => {
                    this.$message.error(`提交数据失败，失败码：${err.response.status}`);
                });

                return;
            }
            const metadata = this.formFields.map((i) => {
                return {
                    metadataId: i.id,
                    dataType: i.dataType,
                    code: i.code,
                    name: i.name,
                    seq: i.seq,
                    value: i.value,
                };
            });
            // console.log(metadata);
            const data = {
                files: this.$refs['uploadFile'].uploadFiles,
                database: this.form.quoteData.dbId ? this.form.quoteData.dbId : '',
                metadata: JSON.stringify(metadata),
            };
            // console.log(data);
            const bodyFormData = new FormData();
            bodyFormData.append('metaStr', data.metadata);
            if (data.database) {
                bodyFormData.append('database', data.database);
            } else {
                bodyFormData.append('database', '');
            }
            if (data.files.length > 0) {
                for (let i = 0; i < data.files.length; i += 1) {
                    let file = data.files[i];
                    // console.log(file);
                    if (file) {
                        let nFile = new File([file.raw], file.name, { type: file.raw.type });
                        bodyFormData.append('files', nFile);
                    }
                }
            }
            // return;
            this.$axios({
                method: 'post',
                url: `/user/folder/${this.folderId}/literature`,
                data: bodyFormData,
                headers: { "Content-Type": "multipart/form-data" },
            }).then((res) => {
                if (res.c === 0) {
                    this.$message.success('上传成功');
                    this.handleOnClose();
                    this.visible = false;
                    Utils.$emit('refreshHome');
                }
            }).catch((err) => {
                this.$message.error(`提交数据失败，失败码：${err.response.status}`);
            });
        },
        readFile(file, callback) {
            let result;
            let reader = new FileReader();
            reader.onloadend = (e) => {
                result = e.target.result;
                // let blob = new Blob(e.target.result, { type: file.raw.type });
                // let nFile = new File([blob], file.name);
                callback(result);
            };
            if (file) {
                reader.readAsBinaryString(file.raw);
            }
        },
        dialogOnClose() {
            this.visible = false;
            this.handleOnClose();
        },
        nameFormat(row, column) {
            if (row.aliasName === '') {
                return row.dbName;
            } else  {
                return row.aliasName;
            }
        },
    },
}
</script>

<style lang="scss">
.upload-resource-container {
    .el-form-item__error.custom {
        color: #c0c4cc;
    }
}
</style>
