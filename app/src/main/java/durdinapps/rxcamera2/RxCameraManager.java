package durdinapps.rxcamera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.support.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;

public class RxCameraManager {

    @NonNull
    public static Observable<CameraCharacteristics> getCameraCharacteristics(@NonNull final CameraManager cameraManager,
                                                                             @NonNull final String cameraId) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                e.onNext(cameraManager.getCameraCharacteristics(cameraId));
                e.onComplete();
            }
        });
    }

    @NonNull
    public static Observable<String[]> getCameraIdList(final CameraManager cameraManager) {
        return Observable.create(new ObservableOnSubscribe<String[]>() {
            @Override
            public void subscribe(ObservableEmitter<String[]> e) throws Exception {
                e.onNext(cameraManager.getCameraIdList());
                e.onComplete();
            }
        });
    }

    @NonNull
    public static Observable<OpenCameraEvent> openCamera(@NonNull final CameraManager cameraManager,
                                        @NonNull final String cameraId,
                                        @NonNull final Handler handler) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(final ObservableEmitter e) throws Exception {
                try {
                    cameraManager.openCamera(cameraId, new CameraDevice.StateCallback() {
                        @Override
                        public void onOpened(CameraDevice camera) {
                            e.onNext(new OpenCameraEvent(camera, OpenCameraEvent.EventType.OPENED));
                        }

                        @Override
                        public void onDisconnected(CameraDevice camera) {
                            e.onNext(new OpenCameraEvent(camera, OpenCameraEvent.EventType.DISCONNECTED));
                        }

                        @Override
                        public void onError(CameraDevice camera, int error) {
                            e.onError(new Throwable("Camera error: %d" + error));
                        }
                    }, handler);
                } catch (CameraAccessException | IllegalArgumentException | SecurityException ex) {
                    e.onError(ex);
                }
            }
        });
    }

    @NonNull
    public static Observable<Boolean> registerAvailabilityCallback(@NonNull final CameraManager cameraManager,
                                                                   @NonNull final Handler handler) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(final ObservableEmitter e) throws Exception {
                final CameraManager.AvailabilityCallback availabilityCallback = new CameraManager.AvailabilityCallback() {
                    @Override
                    public void onCameraAvailable(String cameraId) {
                        super.onCameraAvailable(cameraId);
                        e.onNext(true);
                    }

                    @Override
                    public void onCameraUnavailable(String cameraId) {
                        super.onCameraUnavailable(cameraId);
                        e.onNext(false);
                    }
                };

                e.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        cameraManager.unregisterAvailabilityCallback(availabilityCallback);
                    }
                });

                try {
                    cameraManager.registerAvailabilityCallback(availabilityCallback, handler);
                } catch (IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        });
    }

    @NonNull
    public static Observable<Boolean> registerTorchCallback(@NonNull final CameraManager cameraManager,
                                                            @NonNull final Handler handler) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(final ObservableEmitter e) throws Exception {
                final CameraManager.TorchCallback torchCallback = new CameraManager.TorchCallback() {
                    @Override
                    public void onTorchModeUnavailable(String cameraId) {
                        super.onTorchModeUnavailable(cameraId);
                    }

                    @Override
                    public void onTorchModeChanged(String cameraId, boolean enabled) {
                        super.onTorchModeChanged(cameraId, enabled);
                    }
                };

                e.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        cameraManager.unregisterTorchCallback(torchCallback);
                    }
                });
                try {
                    cameraManager.registerTorchCallback(torchCallback, handler);
                } catch (IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        });
    }

    @NonNull
    public static Completable setTorchMode(@NonNull final CameraManager cameraManager,
                                           @NonNull final String cameraId, final boolean enabled) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                try {
                    cameraManager.setTorchMode(cameraId, enabled);
                    e.onComplete();
                } catch (CameraAccessException | IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        });
    }
}
