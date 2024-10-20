<template>
    <el-tree style="background-color: rgb(238, 241, 246);overflow: visible;" 
            :data="checkToolData" 
            :props="defaultProps"
            :render-content="renderContent"/>
</template>

<script>
  export default {
    props: {
      checkToolData:{type:Array,required:true}
    },
    data() {
      return {
        defaultProps: {
          children: 'children',
          label: 'code'
        }
      };
    },
    watch: {},
    computed: {},
    methods: {
      renderContent(h, { node, data }) {
        const displayLabel = `${data.equipName} (${data.code})`;
        return <span>{displayLabel}</span>;
      }
    },
    created() {},
    mounted() {}
  };
</script>
<style lang="scss" scoped>
/* 树形结构内容太长背景色覆盖不全 */
.orgnTree .el-tree--highlight-current .el-tree-node {
  min-width: max-content;
}
</style>