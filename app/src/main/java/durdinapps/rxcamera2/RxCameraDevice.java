package durdinapps.rxcamera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.InputConfiguration;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Surface;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class RxCameraDevice {

    @NonNull
    public static Completable close(@NonNull final CameraDevice cameraDevice) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                cameraDevice.close();
                e.onComplete();
            }
        });
    }

    @NonNull
    public static Single<CaptureRequest.Builder> createCaptureRequest(@NonNull final CameraDevice cameraDevice,
                                                                      @NonNull final int templateType) {
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
    public static Single<CameraCaptureSession> createCaptureSession(@NonNull final CameraDevice cameraDevice,
                                                                    @NonNull final List<Surface> outputs,
                                                                    final Handler handler) {
        return Single.create(new SingleOnSubscribe() {
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
    public static Single<CameraCaptureSession> createCaptureSessionByOutputConfigurations(@NonNull final CameraDevice cameraDevice,
                                                                                          @NonNull final List<Surface> outputs,
                                                                                          final Handler handler) {
        return Single.create(new SingleOnSubscribe() {
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
    public static Single<CaptureRequest.Builder> createReprocessCaptureRequest(@NonNull final CameraDevice cameraDevice,
                                                                               @NonNull final TotalCaptureResult totalCaptureResult) {
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
    public static Single<CameraCaptureSession> createReprocessableCaptureSession(@NonNull final CameraDevice cameraDevice,
                                                                                 @NonNull final InputConfiguration inputConfiguration,
                                                                                 @NonNull final List<Surface> outputs,
                                                                                 final Handler handler) {
        return Single.create(new SingleOnSubscribe() {
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

    @NonNull
    public static Single<String> getId(@NonNull final CameraDevice cameraDevice) {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) throws Exception {
                try {
                    e.onSuccess(cameraDevice.getId());
                } catch (Exception ex) {
                    e.onError(ex);
                }
            }
        });
    }

}
