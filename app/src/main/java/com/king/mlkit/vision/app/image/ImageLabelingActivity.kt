/*
 * Copyright (C) Jenly, MLKit Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.king.mlkit.vision.app.image

import com.google.mlkit.vision.label.ImageLabel
import com.king.app.dialog.AppDialog
import com.king.app.dialog.AppDialogConfig
import com.king.camera.scan.AnalyzeResult
import com.king.mlkit.vision.image.ImageCameraScanActivity

/**
 * 图像标签示例
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ImageLabelingActivity : ImageCameraScanActivity() {
    override fun onScanResultCallback(result: AnalyzeResult<MutableList<ImageLabel>>) {
        cameraScan.setAnalyzeImage(false)
        val buffer = StringBuilder()
        for ((index, data) in result.result.withIndex()) {
            buffer.append("[$index] ").append(data.text).append("\n")
        }
        val config = AppDialogConfig(this)
        config.setContent(buffer).setOnClickConfirm {
            AppDialog.INSTANCE.dismissDialog()
            cameraScan.setAnalyzeImage(true)
        }.setOnClickCancel {
            AppDialog.INSTANCE.dismissDialog()
            finish()
        }
        AppDialog.INSTANCE.showDialog(config, false)
    }
}