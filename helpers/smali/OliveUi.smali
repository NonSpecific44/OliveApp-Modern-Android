.class public final Lolive/app/OliveUi;
.super Ljava/lang/Object;
.source "OliveUi.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lolive/app/OliveUi$Pull;,
        Lolive/app/OliveUi$Adaptive;,
        Lolive/app/OliveUi$InlineProgress;
    }
.end annotation


# direct methods
.method private constructor <init>()V
    .locals 0

    .prologue
    .line 28
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 29
    return-void
.end method

.method public static attach(Landroid/app/Activity;)V
    .locals 0

    .prologue
    .line 60
    invoke-static {p0}, Lolive/app/OliveUi$Pull;->attach(Landroid/app/Activity;)Lolive/app/OliveUi$Pull;

    .line 61
    invoke-static {p0}, Lolive/app/OliveUi$Adaptive;->attach(Landroid/app/Activity;)V

    .line 62
    invoke-static {p0}, Lolive/app/OliveUpdater;->attachSettings(Landroid/app/Activity;)V

    .line 63
    invoke-static {p0}, Lolive/app/OliveArt;->attach(Landroid/app/Activity;)V

    .line 64
    return-void
.end method

.method static dp(Landroid/content/Context;F)F
    .locals 1

    .prologue
    .line 36
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v0

    iget v0, v0, Landroid/util/DisplayMetrics;->density:F

    mul-float/2addr v0, p1

    return v0
.end method

.method static id(Landroid/content/Context;Ljava/lang/String;)I
    .locals 3

    .prologue
    .line 32
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const-string v1, "id"

    invoke-virtual {p0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, p1, v1, v2}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    return v0
.end method

.method public static orientationPolicy(Landroid/app/Activity;)V
    .locals 4

    .prologue
    const/4 v3, 0x1

    const/4 v2, 0x0

    .line 43
    invoke-virtual {p0}, Landroid/app/Activity;->getParent()Landroid/app/Activity;

    move-result-object v0

    if-nez v0, :cond_0

    invoke-virtual {p0}, Landroid/app/Activity;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v0

    iget v0, v0, Landroid/content/res/Configuration;->smallestScreenWidthDp:I

    const/16 v1, 0x258

    if-lt v0, v1, :cond_1

    .line 56
    :cond_0
    :goto_0
    return-void

    .line 46
    :cond_1
    new-array v0, v3, [I

    const v1, 0x1010057

    aput v1, v0, v2

    invoke-virtual {p0, v0}, Landroid/app/Activity;->obtainStyledAttributes([I)Landroid/content/res/TypedArray;

    move-result-object v0

    .line 47
    invoke-virtual {v0, v2, v2}, Landroid/content/res/TypedArray;->getBoolean(IZ)Z

    move-result v1

    .line 48
    invoke-virtual {v0}, Landroid/content/res/TypedArray;->recycle()V

    .line 49
    if-nez v1, :cond_0

    .line 53
    const/4 v0, 0x1

    :try_start_0
    invoke-virtual {p0, v0}, Landroid/app/Activity;->setRequestedOrientation(I)V
    :try_end_0
    .catch Ljava/lang/RuntimeException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    .line 54
    :catch_0
    move-exception v0

    goto :goto_0
.end method

.method public static progress(Landroid/content/Context;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZZ)Landroid/app/ProgressDialog;
    .locals 2

    .prologue
    .line 75
    instance-of v0, p0, Landroid/app/Activity;

    if-eqz v0, :cond_0

    move-object v0, p0

    check-cast v0, Landroid/app/Activity;

    invoke-static {v0}, Lolive/app/OliveUi$Pull;->attach(Landroid/app/Activity;)Lolive/app/OliveUi$Pull;

    move-result-object v0

    move-object v1, v0

    .line 76
    :goto_0
    if-nez v1, :cond_1

    .line 77
    invoke-static {p0, p1, p2, p3, p4}, Landroid/app/ProgressDialog;->show(Landroid/content/Context;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZZ)Landroid/app/ProgressDialog;

    move-result-object v0

    .line 80
    :goto_1
    return-object v0

    .line 75
    :cond_0
    const/4 v0, 0x0

    move-object v1, v0

    goto :goto_0

    .line 79
    :cond_1
    invoke-virtual {v1}, Lolive/app/OliveUi$Pull;->startLoading()V

    .line 80
    new-instance v0, Lolive/app/OliveUi$InlineProgress;

    invoke-direct {v0, p0, v1}, Lolive/app/OliveUi$InlineProgress;-><init>(Landroid/content/Context;Lolive/app/OliveUi$Pull;)V

    goto :goto_1
.end method
