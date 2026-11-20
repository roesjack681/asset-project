<template>
  <Dialog :title="dialogTitle" width="800" v-model="dialogVisible">
    <div id="printArea" class="label-container">
      <div 
        class="label-item" 
        v-for="(item, index) in formData" 
        :key="index" 
        :id="'printArea' + index"
      >
        <!-- 顶部路名 -->
        <div class="road-name">{{ item.raodName }}</div>
        
        <!-- 中部内容区 -->
        <div class="content-area">
          <!-- 左侧二维码 -->
          <div class="qr-code-container">
            <img :src="item.qrcodeFile" alt="二维码" class="qr-code" />
          </div>
          
          <!-- 右侧信息 -->
          <div class="info-container">
            <div class="serial-number">{{ padLeftZero(item.serialNum, 3) }}</div>
            <div class="direction">{{ item.direction }}</div>
            <div class="orgName">包头交警</div>
          </div>
        </div>
      </div>
      <div class="page-break"></div>
    </div>
    
    <template #footer>
      <el-button @click="dialogVisible = false">关 闭</el-button>
    </template>
    
  </Dialog>
</template>
<script setup lang="ts">
import printJS from 'print-js'

import { OpsQrcodeApi, OpsQrcodePrintVO } from '@/api/traffic/opsqrcode'
/** 杆件 表单 */
defineOptions({ name: 'OpsQrCodePrint' })


const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const printType = ref('')
const formData = ref([{
  raodName: '',
  direction: '',
  serialNum: '',
  qrcodeFile: ''
}])

const padLeftZero = (serialNum, length) => {
  return String(serialNum).padStart(length, '0');
}

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = '设备二维码'
  printType.value = type
  resetForm()

  formLoading.value = true
  try {
    formData.value = await OpsQrcodeApi.queryPrintData({
      "printType" : type,
      "keyword" : id
    })
  } finally {
    formLoading.value = false
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    raodName: '',
    direction: '',
    serialNum: '',
    qrcodeFile: ''
  }
}
</script>

<style scoped>
.label-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.label-item {
  width: 100%;
  height: 100%;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.road-name {
  padding: 12px;
  font-size: 64px;
  font-weight: bold;
  text-align: center;
  background-color: #f5f5f5;
  border-bottom: 1px solid #e0e0e0;
}

.content-area {
  display: flex;
  height: calc(100% - 45px);
}

.qr-code-container {
  flex: 1;
  display: flex;
  align-items: left;
  justify-content: center;
  padding: 10px;
}

.qr-code {
  width: 300px;
  height: 300px;
  object-fit: contain;
  display: flex;
  align-items: left;
}

.info-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: left;
  padding: 10px;
}

.serial-number {
  font-size: 100px;
  font-weight: bold;
  margin-bottom: 20px;
  margin-top: -35px;
}

.direction {
  font-size: 36px;
  color: #666;
}

.orgName {
  font-size: 36px;
  color: #666;
  margin-top: 30px;
}
</style>