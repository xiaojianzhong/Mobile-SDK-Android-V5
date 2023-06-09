/*
 * Copyright (c) 2018-2020 DJI
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package dji.v5.ux.visualcamera.wb;

import androidx.annotation.NonNull;
import dji.sdk.keyvalue.key.CameraKey;
import dji.sdk.keyvalue.key.KeyTools;
import dji.sdk.keyvalue.value.camera.CameraWhiteBalanceInfo;
import dji.sdk.keyvalue.value.common.CameraLensType;
import dji.sdk.keyvalue.value.common.ComponentIndexType;
import dji.v5.ux.core.base.DJISDKModel;
import dji.v5.ux.core.base.ICameraIndex;
import dji.v5.ux.core.base.WidgetModel;
import dji.v5.ux.core.communication.ObservableInMemoryKeyedStore;
import dji.v5.ux.core.util.DataProcessor;
import io.reactivex.rxjava3.core.Flowable;

/**
 * Widget Model for the {@link CameraConfigWBWidget} used to define
 * the underlying logic and communication
 */
public class CameraConfigWBWidgetModel extends WidgetModel implements ICameraIndex {
    //region Fields
    private DataProcessor<CameraWhiteBalanceInfo> whiteBalanceProcessor;
    private ComponentIndexType cameraIndex = ComponentIndexType.LEFT_OR_MAIN;
    private CameraLensType lensType = CameraLensType.CAMERA_LENS_ZOOM;
    //endregion

    //region Constructor
    public CameraConfigWBWidgetModel(@NonNull DJISDKModel djiSdkModel,
                                     @NonNull ObservableInMemoryKeyedStore keyedStore) {
        super(djiSdkModel, keyedStore);
        whiteBalanceProcessor = DataProcessor.create(new CameraWhiteBalanceInfo());
    }
    //endregion

    //region Data

    @NonNull
    public ComponentIndexType getCameraIndex() {
        return cameraIndex;
    }

    @NonNull
    public CameraLensType getLensType() {
        return lensType;
    }

    @Override
    public void updateCameraSource(@NonNull ComponentIndexType cameraIndex, @NonNull CameraLensType lensType) {
        this.cameraIndex = cameraIndex;
        this.lensType = lensType;
        restart();
    }

    /**
     * Get the white balance.
     *
     * @return Flowable for the DataProcessor that user should subscribe to.
     */
    public Flowable<CameraWhiteBalanceInfo> getWhiteBalance() {
        return whiteBalanceProcessor.toFlowable();
    }
    //endregion

    //region LifeCycle
    @Override
    protected void inSetup() {
        bindDataProcessor(KeyTools.createCameraKey(CameraKey.KeyWhiteBalance, cameraIndex, lensType), whiteBalanceProcessor);
    }

    @Override
    protected void inCleanup() {
        // Nothing to clean up
    }

    @Override
    protected void updateStates() {
        // Nothing to update
    }
    //endregion
}
