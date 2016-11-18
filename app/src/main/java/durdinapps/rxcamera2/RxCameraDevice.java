package durdinapps.rxcamera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.InputConfiguration;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Surface;

import java.util.List;

import durdinapps.rxcamera2.callbacks.RxCameraStateCallback;
import durdinapps.rxcamera2.wrappers.RxConfigureSessionEvent;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class RxCameraDevice {

    private final CameraDevice cameraDevice;

    public RxCameraDevice(CameraDevice cameraDevice){
        this.cameraDevice = cameraDevice;
    }

    @NonNull
    public CameraDevice getCameraDevice() {
        return cameraDevice;
    }

    @NonNull
    public Completable close() {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                cameraDevice.close();
                e.onComplete();
            }
        });
    }

    @NonNull
    public Single<CaptureRequest.Builder> createCaptureRequest(@NonNull final int templateType) {
        return Single.create(new SingleOnSubscribe<CaptureRequest.Builder>() {
            @Override
            public void subscribe(SingleEmitter<CaptureRequest.Builder> e) throws Exception {
                try {
                    e.onSuccess(cameraDevice.createCaptureRequest(templateType));
                } catch (CameraAccessException | IllegalStateException | IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        });
    }

    @NonNull
    public Single<RxConfigureSessionEvent> createCaptureSession(@NonNull final List<Surface> outputs,
                                                                final Handler handler) {
        return Single.create(new SingleOnSubscribe<RxConfigureSessionEvent>() {
            @Override
            public void subscribe(final SingleEmitter e) throws Exception {
                try {
                    cameraDevice.createCaptureSession(outputs, new RxCameraStateCallback(e), handler);
                } catch (CameraAccessException | IllegalStateException | IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        });
    }

    @NonNull
    public Single<RxConfigureSessionEvent> createCaptureSessionByOutputConfigurations(@NonNull final List<Surface> outputs,
                                                                                      final Handler handler) {
        return Single.create(new SingleOnSubscribe<RxConfigureSessionEvent>() {
            @Override
            public void subscribe(final SingleEmitter e) throws Exception {
                try {
                    cameraDevice.createConstrainedHighSpeedCaptureSession(outputs, new RxCameraStateCallback(e), handler);
                } catch (CameraAccessException | IllegalStateException | IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        });
    }

    @NonNull
    public Single<CaptureRequest.Builder> createReprocessCaptureRequest(@NonNull final TotalCaptureResult totalCaptureResult) {
        return Single.create(new SingleOnSubscribe<CaptureRequest.Builder>() {
            @Override
            public void subscribe(SingleEmitter<CaptureRequest.Builder> e) throws Exception {
                try {
                    e.onSuccess(cameraDevice.createReprocessCaptureRequest(totalCaptureResult));
                } catch (CameraAccessException | IllegalStateException | IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        });
    }

    @NonNull
    public Single<RxConfigureSessionEvent> createReprocessableCaptureSession(@NonNull final InputConfiguration inputConfiguration,
                                                                             @NonNull final List<Surface> outputs,
                                                                             final Handler handler) {
        return Single.create(new SingleOnSubscribe<RxConfigureSessionEvent>() {
            @Override
            public void subscribe(final SingleEmitter e) throws Exception {
                try {
                    cameraDevice.createReprocessableCaptureSession(inputConfiguration, outputs, new RxCameraStateCallback(e), handler);
                } catch (CameraAccessException | IllegalStateException | IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        });
    }

}
