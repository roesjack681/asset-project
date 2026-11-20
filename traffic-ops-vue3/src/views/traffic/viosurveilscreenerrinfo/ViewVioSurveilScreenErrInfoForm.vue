<template>
  <DialogFull class="auditfulldia" :title="dialogTitle" v-model="dialogVisible">
    <div class="fulldiacontent">
      <div style="padding: 10px 20px">
        <el-form
          class="-mb-15px"
          :model="queryParams"
          ref="queryFormRef"
          :inline="true"
          label-width="88px"
        >
          <el-form-item label="无效原因" prop="cwyy">
            <el-select
              v-model="queryParams.cwyy"
              placeholder="请选择无效原因"
              clearable
              filterable
              class="!w-240px"
            >
              <el-option
                v-for="dict in getStrDictOptions(DICT_TYPE.JZ_INVALID_REASON)"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="设备编号" prop="sbbh">
            <el-input
              v-model="queryParams.sbbh"
              placeholder="请输入设备编号"
              clearable
              @keyup.enter="handleQuery"
              class="!w-240px"
            />
          </el-form-item>
          <el-form-item label="行政区域" prop="unitList">
            <el-select v-model="queryParams.unitList" multiple filterable clearable class="!w-240px">
              <el-option
                v-for="dict in getStrDictOptions(DICT_TYPE.GEO_INFO)"
                :key="dict.label"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
            <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="tablecontent">
        <el-row :gutter="12">
          <el-col :span="6" v-for="i in list" :key="i.id">
            <el-card class="box-card" shadow="hover" style="width: 100%;margin-bottom: 12px">
              <el-row :gutter="5">
                <el-col style="margin-bottom: 10px" :span="12">车牌号: {{i.hphm}}</el-col>
                <el-col style="margin-bottom: 10px" :span="12">违法行为: {{i.wfxw}}</el-col>
                <el-col style="margin-bottom: 10px" :span="24">违法时间: {{dateFormatter3(i.wfsj)}}</el-col>
                <el-col style="margin-bottom: 10px" :span="24">无效原因: {{i.cwyy}}</el-col>
              </el-row>
              <el-row :gutter="10">
                <el-col style="margin-bottom: 10px" :span="8" v-if="!i.url1 && !i.url2 && !i.url3">
                  <el-image style="width: 100%; height: 100px">
                    <template #error>
                      <div class="image-slot">
                        <span>暂无图片</span>
                      </div>
                    </template>
                  </el-image>
                </el-col>
                <el-col style="margin-bottom: 10px" :span="8" v-if="i.url1">
                  <el-image style="width: 100%; height: 100px" :src="i.url1" :preview-src-list="getPrevireList(i.url1,i.url2,i.url3)" :hide-on-click-modal="true" fit="cover"/>
                </el-col>
                <el-col style="margin-bottom: 10px" :span="8" v-if="i.url2">
                  <el-image style="width: 100%; height: 100px" :src="i.url2" :preview-src-list="getPrevireList(i.url2, i.url3, i.url1)" :hide-on-click-modal="true" fit="cover"/>
                </el-col>
                <el-col style="margin-bottom: 10px" :span="8" v-if="i.url3">
                  <el-image style="width: 100%; height: 100px" :src="i.url3" :preview-src-list="getPrevireList(i.url3, i.url1,i.url2)" :hide-on-click-modal="true" fit="cover"/>
                </el-col>
              </el-row>
              <el-row :gutter="10">
                <el-col style="margin-bottom: 10px" :span="24">
                  <el-select
                    v-model="i.reviewResult"
                    placeholder="请选择复核原因"
                    clearable
                    filterable
                    style="width: 100%"
                  >
                    <el-option
                      v-for="dict in getStrDictOptions(DICT_TYPE.JZ_INVALID_REASON)"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.label"
                    />
                  </el-select>
                </el-col>
                <el-col style="margin-bottom: 10px" :span="24">
                  <!-- <el-input type="textarea" v-model="i.reviewDesc"  :resize="'none'" :rows="2" placeholder="请输入描述" /> -->
                  <el-select
                    v-model="i.reviewDesc"
                    placeholder="请选择情况描述"
                    clearable
                    filterable
                    style="width: 100%"
                  >
                    <el-option
                      v-for="dict in getStrDictOptions(DICT_TYPE.JZ_REVIEW_DESC)"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.label"
                    />
                  </el-select>
                </el-col>
                <el-col style="margin-bottom: 10px" :span="24">
                  <!-- <el-input type="textarea" v-model="i.reviewDesc"  :resize="'none'" :rows="2" placeholder="请输入描述" /> -->
                  <el-select
                    v-model="i.isVlprAccurate"
                    placeholder="请选择绿牌识别是否准确"
                    clearable
                    filterable
                    style="width: 100%"
                  >
                    <el-option label="是" value="是"/>
                    <el-option label="否" value="否"/>
                  </el-select>
                </el-col>
                <el-col :span="24">
                  <el-button style="width: 100%" type="primary" @click="submitForm(i)" :disabled="formLoading">提交审核</el-button>
                </el-col>
              </el-row>
            </el-card>
          </el-col>
        </el-row>
      </div>
      <div style="padding: 0 20px">
        <PaginationSimple
          :total="total"
          v-model:page="queryParams.pageNo"
          v-model:limit="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
    </div>
  </DialogFull>
</template>
<script setup lang="ts">
import { ViewVioSurveilScreenErrInfoApi, ViewVioSurveilScreenErrInfoVO } from '@/api/traffic/viosurveilscreenerrinfo'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";
import {dateFormatter3} from "@/utils/formatTime";

/** 集指无效违法记录 表单 */
defineOptions({ name: 'ViewVioSurveilScreenErrInfoForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const loading = ref(true) // 列表的加载中
const list = ref<ViewVioSurveilScreenErrInfoVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  cwyy: undefined,
})
const formRules = reactive({
})
const queryFormRef = ref() // 搜索的表单
const formRef = ref() // 表单 Ref
const queryParams = reactive({
  pageNo: 1,
  pageSize: 12,
  cwyy: undefined,
})
/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = '复核'
  formType.value = type
  resetForm()
  getList()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async (item) => {
  // 提交请求
  formLoading.value = true
  try {
    if(!item.reviewResult) {
      return message.error('请选择无效原因')
    }
    const data = {
      id: item.id,
      reviewResult: item.reviewResult,
      reviewDesc: item.reviewDesc,
    }
    await ViewVioSurveilScreenErrInfoApi.saveReviewResult(data)
    message.success(t('common.createSuccess'))
    if(queryParams.pageNo !== 1 && list.value.length === 1) {
      queryParams.pageNo = queryParams.pageNo - 1
    }
    getList()
  } finally {
    formLoading.value = false
  }
}
const getPrevireList = (img1, img2, img3) => {
  var list = []
  if(img1) {
    list.push(img1)
  }
  if(img2) {
    list.push(img2)
  }
  if(img3) {
    list.push(img3)
  }
  return list
}
/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    cwyy: undefined,
  }
  formRef.value?.resetFields()
}
/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await ViewVioSurveilScreenErrInfoApi.getViewVioSurveilScreenErrReviewPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}
/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}
</script>
<style lang="scss">
::v-deep .auditfulldia .el-dialog__body {
  padding: 0px !important;
  height: calc(100% - 50px);
}
.fulldiacontent {
  height: 100%;
  overflow: hidden;
  .tablecontent {
    height: calc(100% - 110px);
    overflow: auto;
    background: #f5f5f5;
    padding: 10px;
    box-sizing: border-box;
    .tablecontentitem_wrapper {
      width: 100%;
      display: flex;
      flex-wrap: wrap;
    }
  }
}
.image-slot {
  background: #f5f7fa;
  text-align: center;
  display: flex;
  color: #c0c4cc;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  font-size: 12px;
}
</style>
