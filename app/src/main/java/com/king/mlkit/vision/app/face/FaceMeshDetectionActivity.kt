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
package com.king.mlkit.vision.app.face

import android.widget.ImageView
import com.google.mlkit.vision.facemesh.FaceMesh
import com.king.app.dialog.AppDialog
import com.king.app.dialog.AppDialogConfig
import com.king.camera.scan.AnalyzeResult
import com.king.mlkit.vision.app.R
import com.king.mlkit.vision.app.drawRect
import com.king.mlkit.vision.facemesh.FaceMeshCameraScanActivity

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
open class FaceMeshDetectionActivity : FaceMeshCameraScanActivity() {

    override fun onScanResultCallback(result: AnalyzeResult<MutableList<FaceMesh>>) {
        cameraScan.setAnalyzeImage(false)
        val bitmap = result.bitmap?.drawRect { canvas, paint ->
            for (data in result.result) {
                canvas.drawRect(data.boundingBox, paint)

                for (point in data.allPoints) {
                    val position = point.position
                    canvas.drawCircle(position.x, position.y, 2f, paint)
                }
            }
        }

        val config = AppDialogConfig(this, R.layout.result_dialog)
        config.setOnClickConfirm {
            AppDialog.INSTANCE.dismissDialog()
            cameraScan.setAnalyzeImage(true)
        }.setOnClickCancel {
            AppDialog.INSTANCE.dismissDialog()
            finish()
        }
        val imageView = config.getView<ImageView>(R.id.ivDialogContent)
        imageView.setImageBitmap(bitmap)
        AppDialog.INSTANCE.showDialog(config, false)
    }
}