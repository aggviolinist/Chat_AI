package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the {@code libs} extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final ActivityLibraryAccessors laccForActivityLibraryAccessors = new ActivityLibraryAccessors(owner);
    private final AndroidxLibraryAccessors laccForAndroidxLibraryAccessors = new AndroidxLibraryAccessors(owner);
    private final BillingLibraryAccessors laccForBillingLibraryAccessors = new BillingLibraryAccessors(owner);
    private final ComposeLibraryAccessors laccForComposeLibraryAccessors = new ComposeLibraryAccessors(owner);
    private final CoreLibraryAccessors laccForCoreLibraryAccessors = new CoreLibraryAccessors(owner);
    private final CoroutinesLibraryAccessors laccForCoroutinesLibraryAccessors = new CoroutinesLibraryAccessors(owner);
    private final EspressoLibraryAccessors laccForEspressoLibraryAccessors = new EspressoLibraryAccessors(owner);
    private final FirebaseLibraryAccessors laccForFirebaseLibraryAccessors = new FirebaseLibraryAccessors(owner);
    private final GoogleLibraryAccessors laccForGoogleLibraryAccessors = new GoogleLibraryAccessors(owner);
    private final HiltLibraryAccessors laccForHiltLibraryAccessors = new HiltLibraryAccessors(owner);
    private final ItextLibraryAccessors laccForItextLibraryAccessors = new ItextLibraryAccessors(owner);
    private final LifecycleLibraryAccessors laccForLifecycleLibraryAccessors = new LifecycleLibraryAccessors(owner);
    private final MaterialLibraryAccessors laccForMaterialLibraryAccessors = new MaterialLibraryAccessors(owner);
    private final NavigationLibraryAccessors laccForNavigationLibraryAccessors = new NavigationLibraryAccessors(owner);
    private final OkhttpLibraryAccessors laccForOkhttpLibraryAccessors = new OkhttpLibraryAccessors(owner);
    private final PlayLibraryAccessors laccForPlayLibraryAccessors = new PlayLibraryAccessors(owner);
    private final RetrofitLibraryAccessors laccForRetrofitLibraryAccessors = new RetrofitLibraryAccessors(owner);
    private final RitchtextLibraryAccessors laccForRitchtextLibraryAccessors = new RitchtextLibraryAccessors(owner);
    private final RoomLibraryAccessors laccForRoomLibraryAccessors = new RoomLibraryAccessors(owner);
    private final RootbeerLibraryAccessors laccForRootbeerLibraryAccessors = new RootbeerLibraryAccessors(owner);
    private final SplashLibraryAccessors laccForSplashLibraryAccessors = new SplashLibraryAccessors(owner);
    private final UiLibraryAccessors laccForUiLibraryAccessors = new UiLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Dependency provider for <b>appcompat</b> with <b>androidx.appcompat:appcompat</b> coordinates and
     * with version reference <b>app.compat</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getAppcompat() {
        return create("appcompat");
    }

    /**
     * Dependency provider for <b>glide</b> with <b>com.github.skydoves:landscapist-glide</b> coordinates and
     * with version reference <b>glide</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getGlide() {
        return create("glide");
    }

    /**
     * Dependency provider for <b>junit</b> with <b>junit:junit</b> coordinates and
     * with version reference <b>junit</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getJunit() {
        return create("junit");
    }

    /**
     * Dependency provider for <b>material3</b> with <b>androidx.compose.material3:material3</b> coordinates and
     * with <b>no version specified</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getMaterial3() {
        return create("material3");
    }

    /**
     * Group of libraries at <b>activity</b>
     */
    public ActivityLibraryAccessors getActivity() {
        return laccForActivityLibraryAccessors;
    }

    /**
     * Group of libraries at <b>androidx</b>
     */
    public AndroidxLibraryAccessors getAndroidx() {
        return laccForAndroidxLibraryAccessors;
    }

    /**
     * Group of libraries at <b>billing</b>
     */
    public BillingLibraryAccessors getBilling() {
        return laccForBillingLibraryAccessors;
    }

    /**
     * Group of libraries at <b>compose</b>
     */
    public ComposeLibraryAccessors getCompose() {
        return laccForComposeLibraryAccessors;
    }

    /**
     * Group of libraries at <b>core</b>
     */
    public CoreLibraryAccessors getCore() {
        return laccForCoreLibraryAccessors;
    }

    /**
     * Group of libraries at <b>coroutines</b>
     */
    public CoroutinesLibraryAccessors getCoroutines() {
        return laccForCoroutinesLibraryAccessors;
    }

    /**
     * Group of libraries at <b>espresso</b>
     */
    public EspressoLibraryAccessors getEspresso() {
        return laccForEspressoLibraryAccessors;
    }

    /**
     * Group of libraries at <b>firebase</b>
     */
    public FirebaseLibraryAccessors getFirebase() {
        return laccForFirebaseLibraryAccessors;
    }

    /**
     * Group of libraries at <b>google</b>
     */
    public GoogleLibraryAccessors getGoogle() {
        return laccForGoogleLibraryAccessors;
    }

    /**
     * Group of libraries at <b>hilt</b>
     */
    public HiltLibraryAccessors getHilt() {
        return laccForHiltLibraryAccessors;
    }

    /**
     * Group of libraries at <b>itext</b>
     */
    public ItextLibraryAccessors getItext() {
        return laccForItextLibraryAccessors;
    }

    /**
     * Group of libraries at <b>lifecycle</b>
     */
    public LifecycleLibraryAccessors getLifecycle() {
        return laccForLifecycleLibraryAccessors;
    }

    /**
     * Group of libraries at <b>material</b>
     */
    public MaterialLibraryAccessors getMaterial() {
        return laccForMaterialLibraryAccessors;
    }

    /**
     * Group of libraries at <b>navigation</b>
     */
    public NavigationLibraryAccessors getNavigation() {
        return laccForNavigationLibraryAccessors;
    }

    /**
     * Group of libraries at <b>okhttp</b>
     */
    public OkhttpLibraryAccessors getOkhttp() {
        return laccForOkhttpLibraryAccessors;
    }

    /**
     * Group of libraries at <b>play</b>
     */
    public PlayLibraryAccessors getPlay() {
        return laccForPlayLibraryAccessors;
    }

    /**
     * Group of libraries at <b>retrofit</b>
     */
    public RetrofitLibraryAccessors getRetrofit() {
        return laccForRetrofitLibraryAccessors;
    }

    /**
     * Group of libraries at <b>ritchtext</b>
     */
    public RitchtextLibraryAccessors getRitchtext() {
        return laccForRitchtextLibraryAccessors;
    }

    /**
     * Group of libraries at <b>room</b>
     */
    public RoomLibraryAccessors getRoom() {
        return laccForRoomLibraryAccessors;
    }

    /**
     * Group of libraries at <b>rootbeer</b>
     */
    public RootbeerLibraryAccessors getRootbeer() {
        return laccForRootbeerLibraryAccessors;
    }

    /**
     * Group of libraries at <b>splash</b>
     */
    public SplashLibraryAccessors getSplash() {
        return laccForSplashLibraryAccessors;
    }

    /**
     * Group of libraries at <b>ui</b>
     */
    public UiLibraryAccessors getUi() {
        return laccForUiLibraryAccessors;
    }

    /**
     * Group of versions at <b>versions</b>
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Group of bundles at <b>bundles</b>
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Group of plugins at <b>plugins</b>
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class ActivityLibraryAccessors extends SubDependencyFactory {
        private final ActivityComposeLibraryAccessors laccForActivityComposeLibraryAccessors = new ActivityComposeLibraryAccessors(owner);

        public ActivityLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>activity.compose</b>
         */
        public ActivityComposeLibraryAccessors getCompose() {
            return laccForActivityComposeLibraryAccessors;
        }

    }

    public static class ActivityComposeLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public ActivityComposeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>compose</b> with <b>androidx.activity:activity-compose</b> coordinates and
         * with version reference <b>activity.compose</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("activity.compose");
        }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.activity:activity-ktx</b> coordinates and
         * with version reference <b>activity.compose</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("activity.compose.ktx");
        }

    }

    public static class AndroidxLibraryAccessors extends SubDependencyFactory {
        private final AndroidxTestLibraryAccessors laccForAndroidxTestLibraryAccessors = new AndroidxTestLibraryAccessors(owner);

        public AndroidxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>androidx.test</b>
         */
        public AndroidxTestLibraryAccessors getTest() {
            return laccForAndroidxTestLibraryAccessors;
        }

    }

    public static class AndroidxTestLibraryAccessors extends SubDependencyFactory {
        private final AndroidxTestExtLibraryAccessors laccForAndroidxTestExtLibraryAccessors = new AndroidxTestExtLibraryAccessors(owner);

        public AndroidxTestLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>androidx.test.ext</b>
         */
        public AndroidxTestExtLibraryAccessors getExt() {
            return laccForAndroidxTestExtLibraryAccessors;
        }

    }

    public static class AndroidxTestExtLibraryAccessors extends SubDependencyFactory {

        public AndroidxTestExtLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>junit</b> with <b>androidx.test.ext:junit</b> coordinates and
         * with version reference <b>androidx.test.ext.junit</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJunit() {
            return create("androidx.test.ext.junit");
        }

    }

    public static class BillingLibraryAccessors extends SubDependencyFactory {
        private final BillingClientLibraryAccessors laccForBillingClientLibraryAccessors = new BillingClientLibraryAccessors(owner);

        public BillingLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>billing.client</b>
         */
        public BillingClientLibraryAccessors getClient() {
            return laccForBillingClientLibraryAccessors;
        }

    }

    public static class BillingClientLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public BillingClientLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>client</b> with <b>com.android.billingclient:billing</b> coordinates and
         * with version reference <b>billing</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("billing.client");
        }

        /**
         * Dependency provider for <b>ktx</b> with <b>com.android.billingclient:billing-ktx</b> coordinates and
         * with version reference <b>billing</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("billing.client.ktx");
        }

    }

    public static class ComposeLibraryAccessors extends SubDependencyFactory {

        public ComposeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>bom</b> with <b>androidx.compose:compose-bom</b> coordinates and
         * with version reference <b>compose.bom</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getBom() {
            return create("compose.bom");
        }

    }

    public static class CoreLibraryAccessors extends SubDependencyFactory {

        public CoreLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.core:core-ktx</b> coordinates and
         * with version reference <b>core.ktx</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("core.ktx");
        }

    }

    public static class CoroutinesLibraryAccessors extends SubDependencyFactory {
        private final CoroutinesPlayLibraryAccessors laccForCoroutinesPlayLibraryAccessors = new CoroutinesPlayLibraryAccessors(owner);

        public CoroutinesLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>android</b> with <b>org.jetbrains.kotlinx:kotlinx-coroutines-android</b> coordinates and
         * with version reference <b>coroutines</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAndroid() {
            return create("coroutines.android");
        }

        /**
         * Dependency provider for <b>core</b> with <b>org.jetbrains.kotlinx:kotlinx-coroutines-core</b> coordinates and
         * with version reference <b>coroutines</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("coroutines.core");
        }

        /**
         * Group of libraries at <b>coroutines.play</b>
         */
        public CoroutinesPlayLibraryAccessors getPlay() {
            return laccForCoroutinesPlayLibraryAccessors;
        }

    }

    public static class CoroutinesPlayLibraryAccessors extends SubDependencyFactory {

        public CoroutinesPlayLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>service</b> with <b>org.jetbrains.kotlinx:kotlinx-coroutines-play-services</b> coordinates and
         * with version reference <b>coroutines</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getService() {
            return create("coroutines.play.service");
        }

    }

    public static class EspressoLibraryAccessors extends SubDependencyFactory {

        public EspressoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>androidx.test.espresso:espresso-core</b> coordinates and
         * with version reference <b>espresso.core</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("espresso.core");
        }

    }

    public static class FirebaseLibraryAccessors extends SubDependencyFactory {

        public FirebaseLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>auth</b> with <b>com.google.firebase:firebase-auth-ktx</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAuth() {
            return create("firebase.auth");
        }

        /**
         * Dependency provider for <b>bom</b> with <b>com.google.firebase:firebase-bom</b> coordinates and
         * with version reference <b>firebase.bom</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getBom() {
            return create("firebase.bom");
        }

        /**
         * Dependency provider for <b>firestore</b> with <b>com.google.firebase:firebase-firestore-ktx</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getFirestore() {
            return create("firebase.firestore");
        }

    }

    public static class GoogleLibraryAccessors extends SubDependencyFactory {

        public GoogleLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>gson</b> with <b>com.google.code.gson:gson</b> coordinates and
         * with version reference <b>gson</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGson() {
            return create("google.gson");
        }

    }

    public static class HiltLibraryAccessors extends SubDependencyFactory {

        public HiltLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>android</b> with <b>com.google.dagger:hilt-android</b> coordinates and
         * with version reference <b>hilt</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAndroid() {
            return create("hilt.android");
        }

        /**
         * Dependency provider for <b>compiler</b> with <b>com.google.dagger:hilt-compiler</b> coordinates and
         * with version reference <b>hilt</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCompiler() {
            return create("hilt.compiler");
        }

    }

    public static class ItextLibraryAccessors extends SubDependencyFactory {

        public ItextLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>android</b> with <b>com.itextpdf.android:kernel-android</b> coordinates and
         * with version reference <b>itext</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAndroid() {
            return create("itext.android");
        }

    }

    public static class LifecycleLibraryAccessors extends SubDependencyFactory {
        private final LifecycleRuntimeLibraryAccessors laccForLifecycleRuntimeLibraryAccessors = new LifecycleRuntimeLibraryAccessors(owner);

        public LifecycleLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>livedata</b> with <b>androidx.lifecycle:lifecycle-livedata-ktx</b> coordinates and
         * with version reference <b>lifecycle.runtime.ktx</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLivedata() {
            return create("lifecycle.livedata");
        }

        /**
         * Dependency provider for <b>viewmodel</b> with <b>androidx.lifecycle:lifecycle-viewmodel-ktx</b> coordinates and
         * with version reference <b>lifecycle.runtime.ktx</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getViewmodel() {
            return create("lifecycle.viewmodel");
        }

        /**
         * Group of libraries at <b>lifecycle.runtime</b>
         */
        public LifecycleRuntimeLibraryAccessors getRuntime() {
            return laccForLifecycleRuntimeLibraryAccessors;
        }

    }

    public static class LifecycleRuntimeLibraryAccessors extends SubDependencyFactory {

        public LifecycleRuntimeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.lifecycle:lifecycle-runtime-ktx</b> coordinates and
         * with version reference <b>lifecycle.runtime.ktx</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("lifecycle.runtime.ktx");
        }

    }

    public static class MaterialLibraryAccessors extends SubDependencyFactory {

        public MaterialLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>icons</b> with <b>androidx.compose.material:material-icons-extended</b> coordinates and
         * with version reference <b>material.icons</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getIcons() {
            return create("material.icons");
        }

    }

    public static class NavigationLibraryAccessors extends SubDependencyFactory {

        public NavigationLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>compose</b> with <b>androidx.navigation:navigation-compose</b> coordinates and
         * with version reference <b>navigation.compose</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCompose() {
            return create("navigation.compose");
        }

        /**
         * Dependency provider for <b>hilt</b> with <b>androidx.hilt:hilt-navigation-compose</b> coordinates and
         * with version reference <b>nav.hilt</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getHilt() {
            return create("navigation.hilt");
        }

    }

    public static class OkhttpLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public OkhttpLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>okhttp</b> with <b>com.squareup.okhttp3:okhttp</b> coordinates and
         * with version reference <b>okhttp</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("okhttp");
        }

        /**
         * Dependency provider for <b>logging</b> with <b>com.squareup.okhttp3:logging-interceptor</b> coordinates and
         * with version reference <b>okhttp</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLogging() {
            return create("okhttp.logging");
        }

    }

    public static class PlayLibraryAccessors extends SubDependencyFactory {
        private final PlayServicesLibraryAccessors laccForPlayServicesLibraryAccessors = new PlayServicesLibraryAccessors(owner);

        public PlayLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>play.services</b>
         */
        public PlayServicesLibraryAccessors getServices() {
            return laccForPlayServicesLibraryAccessors;
        }

    }

    public static class PlayServicesLibraryAccessors extends SubDependencyFactory {
        private final PlayServicesAdmobLibraryAccessors laccForPlayServicesAdmobLibraryAccessors = new PlayServicesAdmobLibraryAccessors(owner);

        public PlayServicesLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>auth</b> with <b>com.google.android.gms:play-services-auth</b> coordinates and
         * with version reference <b>play.services.auth</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAuth() {
            return create("play.services.auth");
        }

        /**
         * Group of libraries at <b>play.services.admob</b>
         */
        public PlayServicesAdmobLibraryAccessors getAdmob() {
            return laccForPlayServicesAdmobLibraryAccessors;
        }

    }

    public static class PlayServicesAdmobLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public PlayServicesAdmobLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>admob</b> with <b>com.google.android.gms:play-services-ads</b> coordinates and
         * with version reference <b>play.services</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("play.services.admob");
        }

        /**
         * Dependency provider for <b>lite</b> with <b>com.google.android.gms:play-services-ads-lite</b> coordinates and
         * with version reference <b>play.services</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLite() {
            return create("play.services.admob.lite");
        }

    }

    public static class RetrofitLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public RetrofitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>retrofit</b> with <b>com.squareup.retrofit2:retrofit</b> coordinates and
         * with version reference <b>retrofit</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("retrofit");
        }

        /**
         * Dependency provider for <b>convertor</b> with <b>com.squareup.retrofit2:converter-gson</b> coordinates and
         * with version reference <b>retrofit</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getConvertor() {
            return create("retrofit.convertor");
        }

    }

    public static class RitchtextLibraryAccessors extends SubDependencyFactory {
        private final RitchtextUiLibraryAccessors laccForRitchtextUiLibraryAccessors = new RitchtextUiLibraryAccessors(owner);

        public RitchtextLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>common</b> with <b>com.halilibo.compose-richtext:richtext-commonmark</b> coordinates and
         * with version reference <b>ritchtext</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCommon() {
            return create("ritchtext.common");
        }

        /**
         * Group of libraries at <b>ritchtext.ui</b>
         */
        public RitchtextUiLibraryAccessors getUi() {
            return laccForRitchtextUiLibraryAccessors;
        }

    }

    public static class RitchtextUiLibraryAccessors extends SubDependencyFactory {

        public RitchtextUiLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>material</b> with <b>com.halilibo.compose-richtext:richtext-ui-material</b> coordinates and
         * with version reference <b>ritchtext</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getMaterial() {
            return create("ritchtext.ui.material");
        }

        /**
         * Dependency provider for <b>material3</b> with <b>com.halilibo.compose-richtext:richtext-ui-material3</b> coordinates and
         * with version reference <b>ritchtext</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getMaterial3() {
            return create("ritchtext.ui.material3");
        }

    }

    public static class RoomLibraryAccessors extends SubDependencyFactory {

        public RoomLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>compiler</b> with <b>androidx.room:room-compiler</b> coordinates and
         * with version reference <b>room</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCompiler() {
            return create("room.compiler");
        }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.room:room-ktx</b> coordinates and
         * with version reference <b>room</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("room.ktx");
        }

        /**
         * Dependency provider for <b>runtime</b> with <b>androidx.room:room-runtime</b> coordinates and
         * with version reference <b>room</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getRuntime() {
            return create("room.runtime");
        }

    }

    public static class RootbeerLibraryAccessors extends SubDependencyFactory {

        public RootbeerLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>lib</b> with <b>com.scottyab:rootbeer-lib</b> coordinates and
         * with version reference <b>rootbeer</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLib() {
            return create("rootbeer.lib");
        }

    }

    public static class SplashLibraryAccessors extends SubDependencyFactory {

        public SplashLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>androidx.core:core-splashscreen</b> coordinates and
         * with version reference <b>splash.api</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("splash.api");
        }

    }

    public static class UiLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {
        private final UiTestLibraryAccessors laccForUiTestLibraryAccessors = new UiTestLibraryAccessors(owner);
        private final UiToolingLibraryAccessors laccForUiToolingLibraryAccessors = new UiToolingLibraryAccessors(owner);

        public UiLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ui</b> with <b>androidx.compose.ui:ui</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("ui");
        }

        /**
         * Dependency provider for <b>graphics</b> with <b>androidx.compose.ui:ui-graphics</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGraphics() {
            return create("ui.graphics");
        }

        /**
         * Group of libraries at <b>ui.test</b>
         */
        public UiTestLibraryAccessors getTest() {
            return laccForUiTestLibraryAccessors;
        }

        /**
         * Group of libraries at <b>ui.tooling</b>
         */
        public UiToolingLibraryAccessors getTooling() {
            return laccForUiToolingLibraryAccessors;
        }

    }

    public static class UiTestLibraryAccessors extends SubDependencyFactory {

        public UiTestLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>junit4</b> with <b>androidx.compose.ui:ui-test-junit4</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJunit4() {
            return create("ui.test.junit4");
        }

        /**
         * Dependency provider for <b>manifest</b> with <b>androidx.compose.ui:ui-test-manifest</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getManifest() {
            return create("ui.test.manifest");
        }

    }

    public static class UiToolingLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public UiToolingLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>tooling</b> with <b>androidx.compose.ui:ui-tooling</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("ui.tooling");
        }

        /**
         * Dependency provider for <b>preview</b> with <b>androidx.compose.ui:ui-tooling-preview</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getPreview() {
            return create("ui.tooling.preview");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        private final ActivityVersionAccessors vaccForActivityVersionAccessors = new ActivityVersionAccessors(providers, config);
        private final AndroidxVersionAccessors vaccForAndroidxVersionAccessors = new AndroidxVersionAccessors(providers, config);
        private final AppVersionAccessors vaccForAppVersionAccessors = new AppVersionAccessors(providers, config);
        private final ComposeVersionAccessors vaccForComposeVersionAccessors = new ComposeVersionAccessors(providers, config);
        private final CoreVersionAccessors vaccForCoreVersionAccessors = new CoreVersionAccessors(providers, config);
        private final EspressoVersionAccessors vaccForEspressoVersionAccessors = new EspressoVersionAccessors(providers, config);
        private final FirebaseVersionAccessors vaccForFirebaseVersionAccessors = new FirebaseVersionAccessors(providers, config);
        private final LifecycleVersionAccessors vaccForLifecycleVersionAccessors = new LifecycleVersionAccessors(providers, config);
        private final MaterialVersionAccessors vaccForMaterialVersionAccessors = new MaterialVersionAccessors(providers, config);
        private final NavVersionAccessors vaccForNavVersionAccessors = new NavVersionAccessors(providers, config);
        private final NavigationVersionAccessors vaccForNavigationVersionAccessors = new NavigationVersionAccessors(providers, config);
        private final PlayVersionAccessors vaccForPlayVersionAccessors = new PlayVersionAccessors(providers, config);
        private final SplashVersionAccessors vaccForSplashVersionAccessors = new SplashVersionAccessors(providers, config);
        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>agp</b> with value <b>8.8.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAgp() { return getVersion("agp"); }

        /**
         * Version alias <b>billing</b> with value <b>6.0.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getBilling() { return getVersion("billing"); }

        /**
         * Version alias <b>coroutines</b> with value <b>1.6.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCoroutines() { return getVersion("coroutines"); }

        /**
         * Version alias <b>glide</b> with value <b>2.2.6</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getGlide() { return getVersion("glide"); }

        /**
         * Version alias <b>gson</b> with value <b>2.10</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getGson() { return getVersion("gson"); }

        /**
         * Version alias <b>hilt</b> with value <b>2.44.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getHilt() { return getVersion("hilt"); }

        /**
         * Version alias <b>itext</b> with value <b>7.2.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getItext() { return getVersion("itext"); }

        /**
         * Version alias <b>junit</b> with value <b>4.13.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJunit() { return getVersion("junit"); }

        /**
         * Version alias <b>kotlin</b> with value <b>1.8.10</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getKotlin() { return getVersion("kotlin"); }

        /**
         * Version alias <b>okhttp</b> with value <b>4.9.3</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getOkhttp() { return getVersion("okhttp"); }

        /**
         * Version alias <b>psp</b> with value <b>4.4.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getPsp() { return getVersion("psp"); }

        /**
         * Version alias <b>retrofit</b> with value <b>2.9.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getRetrofit() { return getVersion("retrofit"); }

        /**
         * Version alias <b>ritchtext</b> with value <b>0.16.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getRitchtext() { return getVersion("ritchtext"); }

        /**
         * Version alias <b>room</b> with value <b>2.5.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getRoom() { return getVersion("room"); }

        /**
         * Version alias <b>rootbeer</b> with value <b>0.1.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getRootbeer() { return getVersion("rootbeer"); }

        /**
         * Group of versions at <b>versions.activity</b>
         */
        public ActivityVersionAccessors getActivity() {
            return vaccForActivityVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.androidx</b>
         */
        public AndroidxVersionAccessors getAndroidx() {
            return vaccForAndroidxVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.app</b>
         */
        public AppVersionAccessors getApp() {
            return vaccForAppVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.compose</b>
         */
        public ComposeVersionAccessors getCompose() {
            return vaccForComposeVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.core</b>
         */
        public CoreVersionAccessors getCore() {
            return vaccForCoreVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.espresso</b>
         */
        public EspressoVersionAccessors getEspresso() {
            return vaccForEspressoVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.firebase</b>
         */
        public FirebaseVersionAccessors getFirebase() {
            return vaccForFirebaseVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.lifecycle</b>
         */
        public LifecycleVersionAccessors getLifecycle() {
            return vaccForLifecycleVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.material</b>
         */
        public MaterialVersionAccessors getMaterial() {
            return vaccForMaterialVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.nav</b>
         */
        public NavVersionAccessors getNav() {
            return vaccForNavVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.navigation</b>
         */
        public NavigationVersionAccessors getNavigation() {
            return vaccForNavigationVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.play</b>
         */
        public PlayVersionAccessors getPlay() {
            return vaccForPlayVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.splash</b>
         */
        public SplashVersionAccessors getSplash() {
            return vaccForSplashVersionAccessors;
        }

    }

    public static class ActivityVersionAccessors extends VersionFactory  {

        public ActivityVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>activity.compose</b> with value <b>1.8.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCompose() { return getVersion("activity.compose"); }

    }

    public static class AndroidxVersionAccessors extends VersionFactory  {

        private final AndroidxTestVersionAccessors vaccForAndroidxTestVersionAccessors = new AndroidxTestVersionAccessors(providers, config);
        public AndroidxVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.androidx.test</b>
         */
        public AndroidxTestVersionAccessors getTest() {
            return vaccForAndroidxTestVersionAccessors;
        }

    }

    public static class AndroidxTestVersionAccessors extends VersionFactory  {

        private final AndroidxTestExtVersionAccessors vaccForAndroidxTestExtVersionAccessors = new AndroidxTestExtVersionAccessors(providers, config);
        public AndroidxTestVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.androidx.test.ext</b>
         */
        public AndroidxTestExtVersionAccessors getExt() {
            return vaccForAndroidxTestExtVersionAccessors;
        }

    }

    public static class AndroidxTestExtVersionAccessors extends VersionFactory  {

        public AndroidxTestExtVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>androidx.test.ext.junit</b> with value <b>1.1.5</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJunit() { return getVersion("androidx.test.ext.junit"); }

    }

    public static class AppVersionAccessors extends VersionFactory  {

        public AppVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>app.compat</b> with value <b>1.6.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCompat() { return getVersion("app.compat"); }

    }

    public static class ComposeVersionAccessors extends VersionFactory  {

        public ComposeVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>compose.bom</b> with value <b>2023.03.00</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getBom() { return getVersion("compose.bom"); }

    }

    public static class CoreVersionAccessors extends VersionFactory  {

        public CoreVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>core.ktx</b> with value <b>1.12.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getKtx() { return getVersion("core.ktx"); }

    }

    public static class EspressoVersionAccessors extends VersionFactory  {

        public EspressoVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>espresso.core</b> with value <b>3.5.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCore() { return getVersion("espresso.core"); }

    }

    public static class FirebaseVersionAccessors extends VersionFactory  {

        public FirebaseVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>firebase.bom</b> with value <b>29.0.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getBom() { return getVersion("firebase.bom"); }

    }

    public static class LifecycleVersionAccessors extends VersionFactory  {

        private final LifecycleRuntimeVersionAccessors vaccForLifecycleRuntimeVersionAccessors = new LifecycleRuntimeVersionAccessors(providers, config);
        public LifecycleVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.lifecycle.runtime</b>
         */
        public LifecycleRuntimeVersionAccessors getRuntime() {
            return vaccForLifecycleRuntimeVersionAccessors;
        }

    }

    public static class LifecycleRuntimeVersionAccessors extends VersionFactory  {

        public LifecycleRuntimeVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>lifecycle.runtime.ktx</b> with value <b>2.6.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getKtx() { return getVersion("lifecycle.runtime.ktx"); }

    }

    public static class MaterialVersionAccessors extends VersionFactory  {

        public MaterialVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>material.icons</b> with value <b>1.5.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getIcons() { return getVersion("material.icons"); }

    }

    public static class NavVersionAccessors extends VersionFactory  {

        public NavVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>nav.hilt</b> with value <b>1.0.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getHilt() { return getVersion("nav.hilt"); }

    }

    public static class NavigationVersionAccessors extends VersionFactory  {

        public NavigationVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>navigation.compose</b> with value <b>2.7.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCompose() { return getVersion("navigation.compose"); }

    }

    public static class PlayVersionAccessors extends VersionFactory  {

        private final PlayServicesVersionAccessors vaccForPlayServicesVersionAccessors = new PlayServicesVersionAccessors(providers, config);
        public PlayVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.play.services</b>
         */
        public PlayServicesVersionAccessors getServices() {
            return vaccForPlayServicesVersionAccessors;
        }

    }

    public static class PlayServicesVersionAccessors extends VersionFactory  implements VersionNotationSupplier {

        public PlayServicesVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>play.services</b> with value <b>22.0.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> asProvider() { return getVersion("play.services"); }

        /**
         * Version alias <b>play.services.auth</b> with value <b>20.5.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAuth() { return getVersion("play.services.auth"); }

    }

    public static class SplashVersionAccessors extends VersionFactory  {

        public SplashVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>splash.api</b> with value <b>1.0.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getApi() { return getVersion("splash.api"); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

    public static class PluginAccessors extends PluginFactory {

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>androidApplication</b> with plugin id <b>com.android.application</b> and
         * with version reference <b>agp</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getAndroidApplication() { return createPlugin("androidApplication"); }

        /**
         * Plugin provider for <b>daggerHilt</b> with plugin id <b>com.google.dagger.hilt.android</b> and
         * with version reference <b>hilt</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getDaggerHilt() { return createPlugin("daggerHilt"); }

        /**
         * Plugin provider for <b>kotlinAndroid</b> with plugin id <b>org.jetbrains.kotlin.android</b> and
         * with version reference <b>kotlin</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getKotlinAndroid() { return createPlugin("kotlinAndroid"); }

        /**
         * Plugin provider for <b>kotlinKapt</b> with plugin id <b>org.jetbrains.kotlin.kapt</b> and
         * with version reference <b>kotlin</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getKotlinKapt() { return createPlugin("kotlinKapt"); }

        /**
         * Plugin provider for <b>playServices</b> with plugin id <b>com.google.gms.google-services</b> and
         * with version reference <b>psp</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getPlayServices() { return createPlugin("playServices"); }

    }

}
