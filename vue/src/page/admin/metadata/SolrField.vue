<template>
    <div>
        <el-dialog :title="title" :visible.sync="visible" append-to-body width="460px">
            <el-table :data="fieldData" highlight-current-row @current-change="handleCurrentChange">
                <el-table-column property="code" label="字段"></el-table-column>
                <el-table-column property="name" label="名称"></el-table-column>
            </el-table>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" icon="iconfont icon-add-new" @click="dialogOnSubmit">
                    <span style="padding-left: 10px;"><b>确定</b></span>
                </el-button>
                <el-button icon="iconfont icon-jieshu" @click.native="dialogOnClose">
                    <span style="padding-left: 10px;"><b>关闭</b></span>
                </el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import Utils from '../../../util/utils';
export default {
    name: 'solrField',
    data() {
        return {
            title: '',
            visible: false,
            fieldData: [],
            docType: null,
            keywords: '',
            selectedSolr: null,
        };
    },
    watch: {
        docType(val) {
            // console.log(val);
            if (val) {
                this.initFields();
            }
        },
    },
    methods: {
        initFields() {
            // console.log(this.docType);
            if (!this.docType) {
                return;
            }
            this.$axios({
            method: 'get',
                url: `/external/solr/type/${this.docType}?name=${this.keywords}`,
            }).then((res) => {
                // console.log(res);
                this.fieldData = res.ctt;
            });
        },
        handleCurrentChange(val) {
            this.selectedSolr = val;
        },
        dialogOnClose() {
            this.visible = false;
        },
        dialogOnSubmit() {
            Utils.$emit('selectedSolr', this.selectedSolr);
            this.dialogOnClose();
        },
    },
}
</script>

<style lang="scss">

</style>
