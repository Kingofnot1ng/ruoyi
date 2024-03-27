<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="记录id" prop="recordId">
        <el-input
          v-model="queryParams.recordId"
          placeholder="请输入记录id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品id" prop="productId">
        <el-input
          v-model="queryParams.productId"
          placeholder="请输入商品id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入商品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新时间" prop="lastUpdateTime">
        <el-date-picker clearable
          v-model="queryParams.lastUpdateTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择更新时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['business:inventory:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['business:inventory:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['business:inventory:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:inventory:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inventoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="记录id" align="center" prop="recordId" />
      <el-table-column label="商品id" align="center" prop="productId" />
      <el-table-column label="商品名称" align="center" prop="productName" />
      <el-table-column label="商品数量" align="center"  >
        <template slot-scope="scope">
          <el-row>
            <el-col :span="6">
              <el-button 
                type="primary" 
                size="mini"
                icon="el-icon-minus" 
                @click.exact="handleDecrementQuantity(scope.row,1)"
                @click.shift="handleDecrementQuantity(scope.row,10)"
                @click.ctrl="handleDecrementQuantity(scope.row,100)"
                circle
              ></el-button>
            </el-col>
            <el-col :span="12">
              <el-input v-model="scope.row.stockQuantity" disabled></el-input>
            </el-col>
            <el-col :span="6">
              <el-button 
                type="primary"
                size="mini"             
                icon="el-icon-plus" 
                @click.exact="handleIncrementQuantity(scope.row,1)"
                @click.shift="handleIncrementQuantity(scope.row,10)"
                @click.ctrl="handleIncrementQuantity(scope.row,100)"
                circle
              ></el-button>
            </el-col>
          </el-row>
        </template>
      </el-table-column>   
      <el-table-column label="更新时间" align="center" prop="lastUpdateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastUpdateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:inventory:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:inventory:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改库存对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="商品id" prop="productId">
          <!-- <el-input v-model="form.productId" placeholder="请输入商品id" /> -->
          <el-select v-model="form.productId" placeholder="请输入商品id" @change="handleSelectedItemChange('id')">
            <el-option
              v-for="item in productList"
              :key="item.productId"
              :label="item.productId"
              :value="item.productId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品名称" prop="productName">
          <!-- <el-input v-model="form.productName" placeholder="请输入商品名称" /> -->
          <el-select v-model="form.productName" placeholder="请输入商品名称" @change="handleSelectedItemChange('name')">
            <el-option
              v-for="item in productList"
              :key="item.productName"
              :label="item.productName"
              :value="item.productName"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品数量" prop="stockQuantity">
          <el-input v-model="form.stockQuantity" placeholder="请输入商品数量" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 隐藏的行表单 -->
    <el-form ref="rowForm" :model="rowForm" hidden>
      <el-form-item label="商品id" prop="productId">
        <el-input v-model="rowForm.productId"/>
      </el-form-item>
      <el-form-item label="商品名称" prop="productName">
        <el-input v-model="rowForm.productName"/>
      </el-form-item>
      <el-form-item label="商品数量" prop="stockQuantity">
        <el-input v-model="rowForm.stockQuantity"/>
      </el-form-item>  
    </el-form>
  </div>
</template>

<script>
import { listInventory, getInventory, delInventory, addInventory, updateInventory } from "@/api/business/inventory";
import { listProduct } from "@/api/business/product" ;
export default {
  name: "Inventory",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 库存表格数据
      inventoryList: [],
      // 商品表格数据
      productList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        recordId: null,
        productId: null,
        productName: null,
        lastUpdateTime: null
      },
      // 商品查询参数
      queryParams_product: {
        pageNum: 1,
        pageSize: 10,
        productId: null,
        productCode: null,
        productName: null,
        description: null,
        price: null,
        supplierId: null,
      },
      // 表单参数
      form: {},
      // 行表单
      rowForm: {},
      // 表单校验
      rules: {
        productId: [
          { required: true, message: "商品id不能为空", trigger: "blur" }
        ],
        productName: [
          { required: true, message: "商品名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询库存列表 */
    getList() {
      this.loading = true;
      Promise.all([
        listInventory(this.queryParams),
        listProduct(this.queryParams_product)
      ]).then(([inventoryResponse, productResponse]) => {
        this.inventoryList = inventoryResponse.rows;
        this.total = inventoryResponse.total;
        this.productList = productResponse.rows;
        this.loading = false; // 在所有异步操作完成后将 loading 状态设置为 false
      }).catch(error => {
        console.error('Error fetching data:', error);
        this.loading = false; // 处理错误时也要确保 loading 状态被设置为 false
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        recordId: null,
        productId: null,
        productName: null,
        stockQuantity: null,
        lastUpdateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.recordId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加库存";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recordId = row.recordId || this.ids
      getInventory(recordId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改库存";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recordId != null) {
            updateInventory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInventory(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
        console.log(this.form)
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const recordIds = row.recordId || this.ids;
      this.$modal.confirm('是否确认删除库存编号为"' + recordIds + '"的数据项？').then(function() {
        return delInventory(recordIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/inventory/export', {
        ...this.queryParams
      }, `inventory_${new Date().getTime()}.xlsx`)
    },
    //下拉列表同步
    handleSelectedItemChange(type){
      // 根据参数 type 判断是哪个下拉框触发的 change 事件
      if (type === 'id') {
        // 更新第二个下拉框的选项
        const item = this.productList.find(option => option.productId === this.form.productId);
        if (item) {
          this.form.productName = item.productName;
        } else {
          this.form.productName = '';
        }
      } else if (type === 'name') {
        // 更新第一个下拉框的选项
        const item = this.productList.find(option => option.productName === this.form.productName);
        if (item) {
          this.form.productId = item.productId;
        } else {
          this.form.productId = '';
        }
      }
    },
    //增加数量操作
    handleIncrementQuantity(row,num){
      this.$set(row,"stockQuantity",row.stockQuantity+num)
      updateInventory(row).then(response => {
        this.$modal.msgSuccess("修改成功");
        this.getList();
      })  
    },
    //减少数量操作
    handleDecrementQuantity(row,num){
      this.$set(row,"stockQuantity",row.stockQuantity-num>0?row.stockQuantity-num:0)
      updateInventory(row).then(response => {
        this.$modal.msgSuccess("修改成功");
        this.getList();
      }) 
    },
  }
};
</script>
