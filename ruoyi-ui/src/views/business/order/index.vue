<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="采购单id" prop="purchaseOrderId">
        <el-input
          v-model="queryParams.purchaseOrderId"
          placeholder="请输入采购单id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商id" prop="supplierId">
        <el-select
          v-model="queryParams.supplierId"
          placeholder="请输入供应商id"
        >
          <el-option
            v-for="item in supplierList"
            :key="item.supplierId"
            :label="item.supplierName"
            :value="item.supplierId"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="经办人id" prop="staffId">
        <el-select v-model="queryParams.staffId" placeholder="经办人id">
          <el-option
            v-for="item in userList"
            :key="item.userId"
            :label="item.userName"
            :value="item.userId"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          clearable
          v-model="queryParams.createTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择创建时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
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
          v-hasPermi="['business:order:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['business:order:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['business:order:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:order:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="orderList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="采购单id" align="center" prop="purchaseOrderId" />
      <el-table-column label="供应商id" align="center" prop="supplierId" />
      <el-table-column label="经办人id" align="center" prop="staffId" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-search"
            @click="handleSearch(scope.row)"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:order:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:order:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改采购单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body  @close="beforeDialogClose()">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :disabled="this.isDisabled">
        <el-form-item label="供应商id" prop="supplierId">
          <el-select v-model="form.supplierId" placeholder="请输入供应商id">
            <el-option
              v-for="item in supplierList"
              :key="item.supplierId"
              :label="item.supplierName"
              :value="item.supplierId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="经办人id" prop="staffId">
          <el-select v-model="form.staffId" placeholder="经办人id">
            <el-option
              v-for="item in userList"
              :key="item.userId"
              :label="item.userName"
              :value="item.userId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-divider content-position="center">采购详情信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleAddPurchaseOrderDetail"
              >添加</el-button
            >
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="handleDeletePurchaseOrderDetail"
              >删除</el-button
            >
          </el-col>
        </el-row>
        <el-table
          :data="purchaseOrderDetailList"
          :row-class-name="rowPurchaseOrderDetailIndex"
          @selection-change="handlePurchaseOrderDetailSelectionChange"
          ref="purchaseOrderDetail"
        >
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column
            label="采购详情id"
            align="center"
            prop="index"
            width="150"
          />
          <el-table-column label="商品id" prop="productId" width="250">
            <template slot-scope="scope">
              <el-select
                v-model="scope.row.productId"
                placeholder="请输入商品id"
              >
                <el-option
                  v-for="item in productList"
                  :key="item.productId"
                  :label="item.productName"
                  :value="item.productId"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="商品数量" prop="quantity" width="150">
            <template slot-scope="scope">
              <el-row>
                <el-col :span="6">
                  <el-button
                    type="primary"
                    size="mini"
                    icon="el-icon-minus"
                    @click.exact="scope.row.quantity--"
                    @click.shift="scope.row.quantity=scope.row.quantity>10?scope.row.quantity-10:0"
                    @click.ctrl="scope.row.quantity=scope.row.quantity>100?scope.row.quantity-100:0"
                    circle
                  ></el-button>
                </el-col>
                <el-col :span="12">
                  <el-input
                    v-model="scope.row.quantity"
                  ></el-input>
                </el-col>
                <el-col :span="6">
                  <el-button
                    type="primary"
                    size="mini"
                    icon="el-icon-plus"
                    @click.exact="scope.row.quantity++"
                    @click.shift="scope.row.quantity=scope.row.quantity+10"
                    @click.ctrl="scope.row.quantity=scope.row.quantity+100"
                    circle
                  ></el-button>
                </el-col>
              </el-row>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listOrder,
  getOrder,
  delOrder,
  addOrder,
  updateOrder,
} from "@/api/business/order";
import { listSupplier } from "@/api/business/supplier";
import { listUser } from "@/api/system/user";
import { listProduct } from "@/api/business/product";

export default {
  name: "Order",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedPurchaseOrderDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 采购单表格数据
      orderList: [],
      // 供应商数据
      supplierList: [],
      // 员工表格数据
      userList: [],
      // 商品表格数据
      productList: [],
      // 采购详情表格数据
      purchaseOrderDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否禁用修改
      isDisabled: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        purchaseOrderId: null,
        supplierId: null,
        staffId: null,
        createTime: null,
      },
      // 供应商查询参数
      queryParams_supplier: {
        pageNum: 1,
        pageSize: 10,
        supplierId: null,
        supplierName: null,
        contact: null,
        address: null,
        remark: null,
      },
      // 用户查询参数
      queryParams_user: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined,
      },
      // 产品查询参数
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
      // 表单校验
      rules: {
        supplierId: [
          { required: true, message: "供应商id不能为空", trigger: "blur" },
        ],
        staffId: [
          { required: true, message: "经办人id不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询采购单列表 */
    getList() {
      this.loading = true;
      Promise.all([
        listOrder(this.queryParams),
        listSupplier(this.queryParams_supplier),
        listUser(this.queryParams_user),
        listProduct(this.productList),
      ])
        .then(
          ([
            orderResponse,
            supplierResponse,
            userResponse,
            productResponse,
          ]) => {
            this.orderList = orderResponse.rows;
            this.total = orderResponse.total;
            this.supplierList = supplierResponse.rows;
            this.userList = userResponse.rows;
            this.productList = productResponse.rows;
            this.loading = false; // 在所有异步操作完成后将 loading 状态设置为 false
          }
        )
        .catch((error) => {
          console.error("Error fetching data:", error);
          this.loading = false; // 处理错误时也要确保 loading 状态被设置为 false
        });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.isDisabled = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        purchaseOrderId: null,
        supplierId: null,
        staffId: null,
        createTime: null,
      };
      this.purchaseOrderDetailList = [];
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
      this.ids = selection.map((item) => item.purchaseOrderId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加采购单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const purchaseOrderId = row.purchaseOrderId || this.ids;
      getOrder(purchaseOrderId).then((response) => {
        this.form = response.data;
        this.purchaseOrderDetailList = response.data.purchaseOrderDetailList;
        this.open = true;
        this.title = "修改采购单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      if(this.isDisabled === true){
        this.open = false;
        this.isDisabled = false;
        return;
      }    
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.form.purchaseOrderDetailList = this.purchaseOrderDetailList;
          if (this.form.purchaseOrderId != null) {
            updateOrder(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const purchaseOrderIds = row.purchaseOrderId || this.ids;
      this.$modal
        .confirm('是否确认删除采购单编号为"' + purchaseOrderIds + '"的数据项？')
        .then(function () {
          return delOrder(purchaseOrderIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 采购详情序号 */
    rowPurchaseOrderDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 采购详情添加按钮操作 */
    handleAddPurchaseOrderDetail() {
      let obj = {};
      obj.productId = "";
      obj.quantity = "";
      this.purchaseOrderDetailList.push(obj);
    },
    /** 采购详情删除按钮操作 */
    handleDeletePurchaseOrderDetail() {
      if (this.checkedPurchaseOrderDetail.length == 0) {
        this.$modal.msgError("请先选择要删除的采购详情数据");
      } else {
        const purchaseOrderDetailList = this.purchaseOrderDetailList;
        const checkedPurchaseOrderDetail = this.checkedPurchaseOrderDetail;
        this.purchaseOrderDetailList = purchaseOrderDetailList.filter(function (
          item
        ) {
          return checkedPurchaseOrderDetail.indexOf(item.index) == -1;
        });
      }
    },
    /** 复选框选中数据 */
    handlePurchaseOrderDetailSelectionChange(selection) {
      this.checkedPurchaseOrderDetail = selection.map((item) => item.index);
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "business/order/export",
        {
          ...this.queryParams,
        },
        `order_${new Date().getTime()}.xlsx`
      );
    },
    //查看操作
    handleSearch(row) {
      this.reset();
      const purchaseOrderId = row.purchaseOrderId || this.ids;
      getOrder(purchaseOrderId).then((response) => {
        this.form = response.data;
        this.purchaseOrderDetailList = response.data.purchaseOrderDetailList;
        this.open = true;
        this.isDisabled = true;
        this.title = "查看采购单";
      });
    },
    beforeDialogClose(){
      this.isDisabled = false;
    }
  },
};
</script>
