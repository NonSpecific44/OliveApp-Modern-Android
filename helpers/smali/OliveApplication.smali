.class public Lolive/app/OliveApplication;
.super Landroid/app/Application;
.source "OliveApplication.java"


# static fields
.field private static final CRASH_FILE:Ljava/lang/String; = "last_crash.txt"


# instance fields
.field private crashChecked:Z

.field private resumed:I


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 28
    invoke-direct {p0}, Landroid/app/Application;-><init>()V

    return-void
.end method

.method static synthetic access$000(Lolive/app/OliveApplication;)Ljava/lang/String;
    .locals 1

    .prologue
    .line 28
    invoke-direct {p0}, Lolive/app/OliveApplication;->versionName()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$100(Lolive/app/OliveApplication;)I
    .locals 1

    .prologue
    .line 28
    iget v0, p0, Lolive/app/OliveApplication;->resumed:I

    return v0
.end method

.method static synthetic access$102(Lolive/app/OliveApplication;I)I
    .locals 0

    .prologue
    .line 28
    iput p1, p0, Lolive/app/OliveApplication;->resumed:I

    return p1
.end method

.method static synthetic access$108(Lolive/app/OliveApplication;)I
    .locals 2

    .prologue
    .line 28
    iget v0, p0, Lolive/app/OliveApplication;->resumed:I

    add-int/lit8 v1, v0, 0x1

    iput v1, p0, Lolive/app/OliveApplication;->resumed:I

    return v0
.end method

.method static synthetic access$200(Lolive/app/OliveApplication;)Z
    .locals 1

    .prologue
    .line 28
    iget-boolean v0, p0, Lolive/app/OliveApplication;->crashChecked:Z

    return v0
.end method

.method static synthetic access$202(Lolive/app/OliveApplication;Z)Z
    .locals 0

    .prologue
    .line 28
    iput-boolean p1, p0, Lolive/app/OliveApplication;->crashChecked:Z

    return p1
.end method

.method static synthetic access$300(Lolive/app/OliveApplication;Landroid/app/Activity;)V
    .locals 0

    .prologue
    .line 28
    invoke-direct {p0, p1}, Lolive/app/OliveApplication;->showLastCrash(Landroid/app/Activity;)V

    return-void
.end method

.method private showLastCrash(Landroid/app/Activity;)V
    .locals 7

    .prologue
    const/16 v6, 0xbb8

    const/4 v0, 0x0

    .line 101
    new-instance v2, Ljava/io/File;

    invoke-virtual {p0}, Lolive/app/OliveApplication;->getFilesDir()Ljava/io/File;

    move-result-object v1

    const-string v3, "last_crash.txt"

    invoke-direct {v2, v1, v3}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 102
    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v1

    if-nez v1, :cond_0

    .line 141
    :goto_0
    return-void

    .line 107
    :cond_0
    :try_start_0
    new-instance v1, Ljava/io/FileInputStream;

    invoke-direct {v1, v2}, Ljava/io/FileInputStream;-><init>(Ljava/io/File;)V

    .line 108
    invoke-virtual {v2}, Ljava/io/File;->length()J

    move-result-wide v4

    long-to-int v3, v4

    new-array v3, v3, [B

    .line 110
    :goto_1
    array-length v4, v3

    if-ge v0, v4, :cond_1

    .line 111
    array-length v4, v3

    sub-int/2addr v4, v0

    invoke-virtual {v1, v3, v0, v4}, Ljava/io/FileInputStream;->read([BII)I

    move-result v4

    .line 112
    if-gez v4, :cond_2

    .line 117
    :cond_1
    invoke-virtual {v1}, Ljava/io/FileInputStream;->close()V

    .line 118
    new-instance v1, Ljava/lang/String;

    const/4 v4, 0x0

    const-string v5, "UTF-8"

    invoke-direct {v1, v3, v4, v0, v5}, Ljava/lang/String;-><init>([BIILjava/lang/String;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_1
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 122
    invoke-virtual {v2}, Ljava/io/File;->delete()Z

    .line 124
    invoke-virtual {p0}, Lolive/app/OliveApplication;->getApplicationContext()Landroid/content/Context;

    move-result-object v2

    .line 126
    :try_start_1
    new-instance v0, Landroid/app/AlertDialog$Builder;

    invoke-direct {v0, p1}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    const-string v3, "Olive closed unexpectedly last time"

    .line 127
    invoke-virtual {v0, v3}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v3

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Tap Copy and paste this report to whoever is fixing the app:\n\n"

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    .line 129
    invoke-virtual {v1}, Ljava/lang/String;->length()I

    move-result v0

    if-le v0, v6, :cond_3

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const/4 v5, 0x0

    const/16 v6, 0xbb8

    invoke-virtual {v1, v5, v6}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v5, "\n\u2026"

    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    :goto_2
    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 128
    invoke-virtual {v3, v0}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const-string v3, "Copy"

    new-instance v4, Lolive/app/OliveApplication$3;

    invoke-direct {v4, p0, v2, v1}, Lolive/app/OliveApplication$3;-><init>(Lolive/app/OliveApplication;Landroid/content/Context;Ljava/lang/String;)V

    .line 130
    invoke-virtual {v0, v3, v4}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const-string v1, "Close"

    const/4 v2, 0x0

    .line 137
    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    .line 138
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->show()Landroid/app/AlertDialog;
    :try_end_1
    .catch Ljava/lang/RuntimeException; {:try_start_1 .. :try_end_1} :catch_0

    goto/16 :goto_0

    .line 139
    :catch_0
    move-exception v0

    goto/16 :goto_0

    .line 115
    :cond_2
    add-int/2addr v0, v4

    .line 116
    goto :goto_1

    .line 119
    :catch_1
    move-exception v0

    .line 122
    invoke-virtual {v2}, Ljava/io/File;->delete()Z

    goto/16 :goto_0

    :catchall_0
    move-exception v0

    invoke-virtual {v2}, Ljava/io/File;->delete()Z

    .line 123
    throw v0

    :cond_3
    move-object v0, v1

    .line 129
    goto :goto_2
.end method

.method private versionName()Ljava/lang/String;
    .locals 3

    .prologue
    .line 94
    :try_start_0
    invoke-virtual {p0}, Lolive/app/OliveApplication;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    invoke-virtual {p0}, Lolive/app/OliveApplication;->getPackageName()Ljava/lang/String;

    move-result-object v1

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/content/pm/PackageManager;->getPackageInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object v0

    iget-object v0, v0, Landroid/content/pm/PackageInfo;->versionName:Ljava/lang/String;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 96
    :goto_0
    return-object v0

    .line 95
    :catch_0
    move-exception v0

    .line 96
    const-string v0, "?"

    goto :goto_0
.end method


# virtual methods
.method public onCreate()V
    .locals 2

    .prologue
    .line 35
    invoke-super {p0}, Landroid/app/Application;->onCreate()V

    .line 36
    sget-object v0, Landroid/os/StrictMode$ThreadPolicy;->LAX:Landroid/os/StrictMode$ThreadPolicy;

    invoke-static {v0}, Landroid/os/StrictMode;->setThreadPolicy(Landroid/os/StrictMode$ThreadPolicy;)V

    .line 38
    invoke-static {}, Ljava/lang/Thread;->getDefaultUncaughtExceptionHandler()Ljava/lang/Thread$UncaughtExceptionHandler;

    move-result-object v0

    .line 39
    new-instance v1, Lolive/app/OliveApplication$1;

    invoke-direct {v1, p0, v0}, Lolive/app/OliveApplication$1;-><init>(Lolive/app/OliveApplication;Ljava/lang/Thread$UncaughtExceptionHandler;)V

    invoke-static {v1}, Ljava/lang/Thread;->setDefaultUncaughtExceptionHandler(Ljava/lang/Thread$UncaughtExceptionHandler;)V

    .line 58
    new-instance v0, Lolive/app/OliveApplication$2;

    invoke-direct {v0, p0}, Lolive/app/OliveApplication$2;-><init>(Lolive/app/OliveApplication;)V

    invoke-virtual {p0, v0}, Lolive/app/OliveApplication;->registerActivityLifecycleCallbacks(Landroid/app/Application$ActivityLifecycleCallbacks;)V

    .line 90
    return-void
.end method
