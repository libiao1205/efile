<template>
    <div class="app-panel" :style="{ height, width }">
        <div class="app-inner">
            <slot></slot>
        </div>
    </div>
</template>

<script>
    export default {
        name: "AppPanel",
        props: {
            height: {
                type: String,
            },
            width: {
                type: String
            }
        }
    }
</script>

<style lang="scss" scoped>
   @import 'sysStatic/css/theme/default.scss';

    .app-panel{
        padding-bottom:20px;
        .app-inner {
            flex: 1;
            padding: 20px 20px 0;
            background-color: white;
            border: 1px solid #BBBBBB;
            box-shadow:0 0 1px $themeColor;
            border-radius: 5px;
            align-items: stretch;
            display: block;
            height: 100%;
        }
    }
</style>
