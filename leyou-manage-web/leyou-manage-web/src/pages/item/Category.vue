<template>
  <v-card>
      <v-flex xs12 sm10>
        <v-tree url="/item/category/list"
                :isEdit="isEdit"
                @handleAdd="handleAdd"
                @handleEdit="handleEdit"
                @handleDelete="handleDelete"
                @handleClick="handleClick"
        />
      </v-flex>
  </v-card>
</template>

<script>
  export default {
    name: "category",
    data() {
      return {
        isEdit:true
      }
    },
    methods: {
      handleAdd(node) {
        //后台接口
        this.$http({
            method: 'post',
            url: '/item/category/add',
            data: this.$qs.stringify(node)
        }).then(res=>{
            this.$message.success("添加节点成功!")
            //局部刷新页面

            console.log(res)
        }).catch(()=>{
            this.$message.error("添加节点失败!")
        })
        console.log("add .... ");
        console.log(node);
      },
      handleEdit(id, name) {
          let data = {id:id,name:name}
          console.log(data)
          //后台接口
          this.$http({
              method: 'put',
              url: '/item/category/edit',
              data: this.$qs.stringify(data)
          }).then(res=>{
              this.$message.success("修改成功!")
          }).catch(()=>{
              this.$message.error("修改失败!")
          })
        //console.log("edit... id: " + id + ", name: " + name)
      },
      handleDelete(id) {
          //let data = {id:id}
          this.$http({
              method: "delete",
              url: "/item/category/delete/"+id,
          }).then(res=>{
              this.$message.success("删除成功!")
          }).catch(()=>{
              this.$message.error("删除失败!")
          })
        console.log("delete ... " + id)
      },
      handleClick(node) {
        console.log(node)
      },
    }
  };
</script>

<style scoped>

</style>
