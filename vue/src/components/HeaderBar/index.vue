<template>
    <div class="sys-header">
        <div class="logo">
            <slot name="logo"></slot>
        </div>
        <div class="mid-bar">
            <slot name="mid-bar"></slot>
        </div>
        <div class="r-bar">
            <slot name="r-bar"></slot>
        </div>
    </div>
    
</template>

<script>
export default {
    name: 'HeaderBar',
}
</script>

<style lang="scss" scoped>
    .sys-header{
        .mid-bar{
            float: left;
        }
        .r-bar{
            float: right;
        }
    }
</style>
