.class public final Lolive/app/OliveUpdater;
.super Ljava/lang/Object;
.source "OliveUpdater.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lolive/app/OliveUpdater$Release;,
        Lolive/app/OliveUpdater$Progress;
    }
.end annotation


# static fields
.field private static final DAY_MS:J = 0x5265c00L

.field private static final PREFS:Ljava/lang/String; = "Olive_Settings"

.field private static volatile busy:Z

.field static latestReleaseUrl:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .prologue
    .line 41
    const-string v0, "https://api.github.com/repos/NonSpecific44/OliveApp-Modern-Android/releases/latest"

    sput-object v0, Lolive/app/OliveUpdater;->latestReleaseUrl:Ljava/lang/String;

    return-void
.end method

.method private constructor <init>()V
    .locals 0

    .prologue
    .line 46
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 47
    return-void
.end method

.method static synthetic access$002(Z)Z
    .locals 0

    .prologue
    .line 40
    sput-boolean p0, Lolive/app/OliveUpdater;->busy:Z

    return p0
.end method

.method static synthetic access$100(Landroid/app/Activity;Lolive/app/OliveUpdater$Release;Ljava/lang/String;)V
    .locals 0

    .prologue
    .line 40
    invoke-static {p0, p1, p2}, Lolive/app/OliveUpdater;->offer(Landroid/app/Activity;Lolive/app/OliveUpdater$Release;Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$200(Landroid/app/Activity;Lolive/app/OliveUpdater$Release;)V
    .locals 0

    .prologue
    .line 40
    invoke-static {p0, p1}, Lolive/app/OliveUpdater;->startUpdate(Landroid/app/Activity;Lolive/app/OliveUpdater$Release;)V

    return-void
.end method

.method public static attachSettings(Landroid/app/Activity;)V
    .locals 4

    .prologue
    .line 430
    const-string v0, "updateRow"

    invoke-static {p0, v0}, Lolive/app/OliveUi;->id(Landroid/content/Context;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    .line 431
    if-nez v1, :cond_0

    .line 443
    :goto_0
    return-void

    .line 434
    :cond_0
    const-string v0, "updateVersion"

    invoke-static {p0, v0}, Lolive/app/OliveUi;->id(Landroid/content/Context;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    .line 435
    if-eqz v0, :cond_1

    .line 436
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Version "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-static {p0}, Lolive/app/OliveUpdater;->installedVersion(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " \u2022 Tap to check for updates"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 438
    :cond_1
    new-instance v0, Lolive/app/OliveUpdater$6;

    invoke-direct {v0, p0}, Lolive/app/OliveUpdater$6;-><init>(Landroid/app/Activity;)V

    invoke-virtual {v1, v0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    goto :goto_0
.end method

.method public static check(Landroid/app/Activity;Z)V
    .locals 3

    .prologue
    .line 110
    sget-boolean v0, Lolive/app/OliveUpdater;->busy:Z

    if-eqz v0, :cond_0

    .line 144
    :goto_0
    return-void

    .line 113
    :cond_0
    const/4 v0, 0x1

    sput-boolean v0, Lolive/app/OliveUpdater;->busy:Z

    .line 114
    new-instance v0, Landroid/os/Handler;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    invoke-direct {v0, v1}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    .line 115
    if-eqz p1, :cond_1

    .line 116
    const-string v1, "Checking for updates\u2026"

    const/4 v2, 0x0

    invoke-static {p0, v1, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    .line 118
    :cond_1
    new-instance v1, Ljava/lang/Thread;

    new-instance v2, Lolive/app/OliveUpdater$1;

    invoke-direct {v2, v0, p0, p1}, Lolive/app/OliveUpdater$1;-><init>(Landroid/os/Handler;Landroid/app/Activity;Z)V

    invoke-direct {v1, v2}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;)V

    .line 143
    invoke-virtual {v1}, Ljava/lang/Thread;->start()V

    goto :goto_0
.end method

.method public static checkOnLaunch(Landroid/app/Activity;)V
    .locals 7

    .prologue
    const/4 v6, 0x0

    .line 99
    const-string v0, "Olive_Settings"

    invoke-virtual {p0, v0, v6}, Landroid/app/Activity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v0

    .line 100
    const-string v1, "update_last_check"

    const-wide/16 v2, 0x0

    invoke-interface {v0, v1, v2, v3}, Landroid/content/SharedPreferences;->getLong(Ljava/lang/String;J)J

    move-result-wide v2

    .line 101
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v4

    sub-long v2, v4, v2

    const-wide/32 v4, 0x5265c00

    cmp-long v1, v2, v4

    if-gez v1, :cond_0

    .line 106
    :goto_0
    return-void

    .line 104
    :cond_0
    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    const-string v1, "update_last_check"

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v2

    invoke-interface {v0, v1, v2, v3}, Landroid/content/SharedPreferences$Editor;->putLong(Ljava/lang/String;J)Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->apply()V

    .line 105
    invoke-static {p0, v6}, Lolive/app/OliveUpdater;->check(Landroid/app/Activity;Z)V

    goto :goto_0
.end method

.method static compare(Ljava/lang/String;Ljava/lang/String;)I
    .locals 6

    .prologue
    const/4 v1, 0x0

    .line 70
    const-string v0, "^[vV]"

    const-string v2, ""

    invoke-virtual {p0, v0, v2}, Ljava/lang/String;->replaceFirst(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v2, "[.\\-+]"

    invoke-virtual {v0, v2}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v4

    .line 71
    const-string v0, "^[vV]"

    const-string v2, ""

    invoke-virtual {p1, v0, v2}, Ljava/lang/String;->replaceFirst(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v2, "[.\\-+]"

    invoke-virtual {v0, v2}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v5

    move v0, v1

    .line 72
    :goto_0
    array-length v2, v4

    array-length v3, v5

    invoke-static {v2, v3}, Ljava/lang/Math;->max(II)I

    move-result v2

    if-ge v0, v2, :cond_4

    .line 73
    array-length v2, v4

    if-ge v0, v2, :cond_0

    aget-object v2, v4, v0

    invoke-static {v2}, Lolive/app/OliveUpdater;->number(Ljava/lang/String;)I

    move-result v2

    .line 74
    :goto_1
    array-length v3, v5

    if-ge v0, v3, :cond_1

    aget-object v3, v5, v0

    invoke-static {v3}, Lolive/app/OliveUpdater;->number(Ljava/lang/String;)I

    move-result v3

    .line 75
    :goto_2
    if-eq v2, v3, :cond_3

    .line 76
    if-ge v2, v3, :cond_2

    const/4 v0, -0x1

    .line 79
    :goto_3
    return v0

    :cond_0
    move v2, v1

    .line 73
    goto :goto_1

    :cond_1
    move v3, v1

    .line 74
    goto :goto_2

    .line 76
    :cond_2
    const/4 v0, 0x1

    goto :goto_3

    .line 72
    :cond_3
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_4
    move v0, v1

    .line 79
    goto :goto_3
.end method

.method static download(Ljava/lang/String;Ljava/io/File;Lolive/app/OliveUpdater$Progress;)V
    .locals 12
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    const-wide/16 v4, 0x0

    .line 290
    new-instance v0, Ljava/net/URL;

    invoke-direct {v0, p0}, Ljava/net/URL;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/net/URL;->openConnection()Ljava/net/URLConnection;

    move-result-object v0

    check-cast v0, Ljava/net/HttpURLConnection;

    .line 291
    const/16 v1, 0x2710

    invoke-virtual {v0, v1}, Ljava/net/HttpURLConnection;->setConnectTimeout(I)V

    .line 292
    const/16 v1, 0x4e20

    invoke-virtual {v0, v1}, Ljava/net/HttpURLConnection;->setReadTimeout(I)V

    .line 293
    const-string v1, "User-Agent"

    const-string v2, "OliveApp-modern"

    invoke-virtual {v0, v1, v2}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 294
    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Ljava/net/HttpURLConnection;->setInstanceFollowRedirects(Z)V

    .line 296
    :try_start_0
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->getResponseCode()I

    move-result v1

    const/16 v2, 0xc8

    if-eq v1, v2, :cond_0

    .line 297
    new-instance v1, Ljava/lang/Exception;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "HTTP "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->getResponseCode()I

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-direct {v1, v2}, Ljava/lang/Exception;-><init>(Ljava/lang/String;)V

    throw v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 315
    :catchall_0
    move-exception v1

    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 316
    throw v1

    .line 299
    :cond_0
    :try_start_1
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->getContentLength()I

    move-result v1

    int-to-long v6, v1

    .line 300
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->getInputStream()Ljava/io/InputStream;

    move-result-object v1

    .line 301
    new-instance v8, Ljava/io/FileOutputStream;

    invoke-direct {v8, p1}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;)V

    .line 302
    const/high16 v2, 0x10000

    new-array v9, v2, [B

    move-wide v2, v4

    .line 305
    :cond_1
    :goto_0
    invoke-virtual {v1, v9}, Ljava/io/InputStream;->read([B)I

    move-result v10

    if-lez v10, :cond_2

    .line 306
    const/4 v11, 0x0

    invoke-virtual {v8, v9, v11, v10}, Ljava/io/OutputStream;->write([BII)V

    .line 307
    int-to-long v10, v10

    add-long/2addr v2, v10

    .line 308
    cmp-long v10, v6, v4

    if-lez v10, :cond_1

    if-eqz p2, :cond_1

    .line 309
    const-wide/16 v10, 0x64

    mul-long/2addr v10, v2

    div-long/2addr v10, v6

    long-to-int v10, v10

    invoke-interface {p2, v10}, Lolive/app/OliveUpdater$Progress;->update(I)V

    goto :goto_0

    .line 312
    :cond_2
    invoke-virtual {v8}, Ljava/io/OutputStream;->close()V

    .line 313
    invoke-virtual {v1}, Ljava/io/InputStream;->close()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 315
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 317
    return-void
.end method

.method static fetchLatest()Lolive/app/OliveUpdater$Release;
    .locals 4

    .prologue
    const/4 v1, 0x0

    .line 149
    :try_start_0
    new-instance v0, Ljava/net/URL;

    sget-object v2, Lolive/app/OliveUpdater;->latestReleaseUrl:Ljava/lang/String;

    invoke-direct {v0, v2}, Ljava/net/URL;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/net/URL;->openConnection()Ljava/net/URLConnection;

    move-result-object v0

    check-cast v0, Ljava/net/HttpURLConnection;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 150
    const/16 v2, 0x1f40

    :try_start_1
    invoke-virtual {v0, v2}, Ljava/net/HttpURLConnection;->setConnectTimeout(I)V

    .line 151
    const/16 v2, 0x1f40

    invoke-virtual {v0, v2}, Ljava/net/HttpURLConnection;->setReadTimeout(I)V

    .line 152
    const-string v2, "Accept"

    const-string v3, "application/vnd.github+json"

    invoke-virtual {v0, v2, v3}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 153
    const-string v2, "User-Agent"

    const-string v3, "OliveApp-modern"

    invoke-virtual {v0, v2, v3}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 154
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->getResponseCode()I
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    move-result v2

    const/16 v3, 0xc8

    if-eq v2, v3, :cond_1

    .line 161
    if-eqz v0, :cond_0

    .line 162
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->disconnect()V

    :cond_0
    move-object v0, v1

    .line 159
    :goto_0
    return-object v0

    .line 157
    :cond_1
    :try_start_2
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->getInputStream()Ljava/io/InputStream;

    move-result-object v2

    invoke-static {v2}, Lolive/app/OliveUpdater;->readAll(Ljava/io/InputStream;)Ljava/lang/String;

    move-result-object v2

    invoke-static {v2}, Lolive/app/OliveUpdater;->parse(Ljava/lang/String;)Lolive/app/OliveUpdater$Release;
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_1
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    move-result-object v1

    .line 161
    if-eqz v0, :cond_2

    .line 162
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->disconnect()V

    :cond_2
    move-object v0, v1

    .line 157
    goto :goto_0

    .line 158
    :catch_0
    move-exception v0

    move-object v0, v1

    .line 161
    :goto_1
    if-eqz v0, :cond_3

    .line 162
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->disconnect()V

    :cond_3
    move-object v0, v1

    .line 159
    goto :goto_0

    .line 161
    :catchall_0
    move-exception v0

    move-object v2, v0

    move-object v3, v1

    :goto_2
    if-eqz v3, :cond_4

    .line 162
    invoke-virtual {v3}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 164
    :cond_4
    throw v2

    .line 161
    :catchall_1
    move-exception v1

    move-object v2, v1

    move-object v3, v0

    goto :goto_2

    .line 158
    :catch_1
    move-exception v2

    goto :goto_1
.end method

.method static install(Landroid/content/Context;Ljava/io/File;)V
    .locals 8
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    const/4 v6, 0x0

    .line 379
    invoke-virtual {p0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/pm/PackageManager;->getPackageInstaller()Landroid/content/pm/PackageInstaller;

    move-result-object v0

    .line 380
    new-instance v1, Landroid/content/pm/PackageInstaller$SessionParams;

    const/4 v2, 0x1

    invoke-direct {v1, v2}, Landroid/content/pm/PackageInstaller$SessionParams;-><init>(I)V

    .line 381
    invoke-virtual {p0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Landroid/content/pm/PackageInstaller$SessionParams;->setAppPackageName(Ljava/lang/String;)V

    .line 382
    invoke-virtual {v0, v1}, Landroid/content/pm/PackageInstaller;->createSession(Landroid/content/pm/PackageInstaller$SessionParams;)I

    move-result v7

    .line 383
    invoke-virtual {v0, v7}, Landroid/content/pm/PackageInstaller;->openSession(I)Landroid/content/pm/PackageInstaller$Session;

    move-result-object v0

    .line 385
    :try_start_0
    const-string v1, "update.apk"

    const-wide/16 v2, 0x0

    invoke-virtual {p1}, Ljava/io/File;->length()J

    move-result-wide v4

    invoke-virtual/range {v0 .. v5}, Landroid/content/pm/PackageInstaller$Session;->openWrite(Ljava/lang/String;JJ)Ljava/io/OutputStream;

    move-result-object v1

    .line 386
    new-instance v2, Ljava/io/FileInputStream;

    invoke-direct {v2, p1}, Ljava/io/FileInputStream;-><init>(Ljava/io/File;)V

    .line 387
    const/high16 v3, 0x10000

    new-array v3, v3, [B

    .line 389
    :goto_0
    invoke-virtual {v2, v3}, Ljava/io/InputStream;->read([B)I

    move-result v4

    if-lez v4, :cond_0

    .line 390
    const/4 v5, 0x0

    invoke-virtual {v1, v3, v5, v4}, Ljava/io/OutputStream;->write([BII)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    .line 399
    :catchall_0
    move-exception v1

    invoke-virtual {v0}, Landroid/content/pm/PackageInstaller$Session;->close()V

    .line 400
    throw v1

    .line 392
    :cond_0
    :try_start_1
    invoke-virtual {v2}, Ljava/io/InputStream;->close()V

    .line 393
    invoke-virtual {v0, v1}, Landroid/content/pm/PackageInstaller$Session;->fsync(Ljava/io/OutputStream;)V

    .line 394
    invoke-virtual {v1}, Ljava/io/OutputStream;->close()V

    .line 395
    new-instance v2, Landroid/content/Intent;

    const-class v1, Lolive/app/OliveUpdateReceiver;

    invoke-direct {v2, p0, v1}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    .line 396
    const/high16 v3, 0x8000000

    sget v1, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v4, 0x1f

    if-lt v1, v4, :cond_1

    const/high16 v1, 0x2000000

    :goto_1
    or-int/2addr v1, v3

    .line 397
    invoke-static {p0, v7, v2, v1}, Landroid/app/PendingIntent;->getBroadcast(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/PendingIntent;->getIntentSender()Landroid/content/IntentSender;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/content/pm/PackageInstaller$Session;->commit(Landroid/content/IntentSender;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 399
    invoke-virtual {v0}, Landroid/content/pm/PackageInstaller$Session;->close()V

    .line 401
    return-void

    :cond_1
    move v1, v6

    .line 396
    goto :goto_1
.end method

.method static installedVersion(Landroid/content/Context;)Ljava/lang/String;
    .locals 3

    .prologue
    .line 62
    :try_start_0
    invoke-virtual {p0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    invoke-virtual {p0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v1

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/content/pm/PackageManager;->getPackageInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object v0

    iget-object v0, v0, Landroid/content/pm/PackageInfo;->versionName:Ljava/lang/String;
    :try_end_0
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    .line 64
    :goto_0
    return-object v0

    .line 63
    :catch_0
    move-exception v0

    .line 64
    const-string v0, ""

    goto :goto_0
.end method

.method static isRelease(Ljava/lang/String;)Z
    .locals 2

    .prologue
    .line 92
    if-eqz p0, :cond_0

    const-string v0, "^[vV]"

    const-string v1, ""

    invoke-virtual {p0, v0, v1}, Ljava/lang/String;->replaceFirst(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "\\d+\\.\\d+\\.\\d+"

    invoke-virtual {v0, v1}, Ljava/lang/String;->matches(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    :goto_0
    return v0

    :cond_0
    const/4 v0, 0x0

    goto :goto_0
.end method

.method private static number(Ljava/lang/String;)I
    .locals 1

    .prologue
    .line 84
    :try_start_0
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    move-result v0

    .line 86
    :goto_0
    return v0

    .line 85
    :catch_0
    move-exception v0

    .line 86
    const/4 v0, 0x0

    goto :goto_0
.end method

.method private static offer(Landroid/app/Activity;Lolive/app/OliveUpdater$Release;Ljava/lang/String;)V
    .locals 4

    .prologue
    const/16 v3, 0x5dc

    .line 196
    iget-object v0, p1, Lolive/app/OliveUpdater$Release;->notes:Ljava/lang/String;

    const-string v1, "\r"

    const-string v2, ""

    invoke-virtual {v0, v1, v2}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "(?m)^#+\\s*"

    const-string v2, ""

    invoke-virtual {v0, v1, v2}, Ljava/lang/String;->replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "**"

    const-string v2, ""

    invoke-virtual {v0, v1, v2}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "`"

    const-string v2, ""

    invoke-virtual {v0, v1, v2}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    .line 197
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v1

    if-le v1, v3, :cond_0

    .line 198
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const/4 v2, 0x0

    invoke-virtual {v0, v2, v3}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "\u2026"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 200
    :cond_0
    new-instance v1, Landroid/app/AlertDialog$Builder;

    invoke-direct {v1, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Update available: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-object v3, p1, Lolive/app/OliveUpdater$Release;->version:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 201
    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "You have "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, ".\n\n"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 202
    invoke-virtual {v1, v0}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const-string v1, "Update"

    new-instance v2, Lolive/app/OliveUpdater$3;

    invoke-direct {v2, p0, p1}, Lolive/app/OliveUpdater$3;-><init>(Landroid/app/Activity;Lolive/app/OliveUpdater$Release;)V

    .line 203
    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const-string v1, "Later"

    const/4 v2, 0x0

    .line 208
    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const-string v1, "Skip this version"

    new-instance v2, Lolive/app/OliveUpdater$2;

    invoke-direct {v2, p0, p1}, Lolive/app/OliveUpdater$2;-><init>(Landroid/app/Activity;Lolive/app/OliveUpdater$Release;)V

    .line 209
    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setNeutralButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    .line 214
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->show()Landroid/app/AlertDialog;

    .line 215
    return-void
.end method

.method static parse(Ljava/lang/String;)Lolive/app/OliveUpdater$Release;
    .locals 7
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    const/4 v1, 0x0

    .line 169
    new-instance v2, Lorg/json/JSONObject;

    invoke-direct {v2, p0}, Lorg/json/JSONObject;-><init>(Ljava/lang/String;)V

    .line 170
    new-instance v0, Lolive/app/OliveUpdater$Release;

    invoke-direct {v0}, Lolive/app/OliveUpdater$Release;-><init>()V

    .line 171
    const-string v3, "tag_name"

    const-string v4, ""

    invoke-virtual {v2, v3, v4}, Lorg/json/JSONObject;->optString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    const-string v4, "^[vV]"

    const-string v5, ""

    invoke-virtual {v3, v4, v5}, Ljava/lang/String;->replaceFirst(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    iput-object v3, v0, Lolive/app/OliveUpdater$Release;->version:Ljava/lang/String;

    .line 172
    iget-object v3, v0, Lolive/app/OliveUpdater$Release;->version:Ljava/lang/String;

    invoke-static {v3}, Lolive/app/OliveUpdater;->isRelease(Ljava/lang/String;)Z

    move-result v3

    if-eqz v3, :cond_0

    const-string v3, "draft"

    invoke-virtual {v2, v3}, Lorg/json/JSONObject;->optBoolean(Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_0

    const-string v3, "prerelease"

    invoke-virtual {v2, v3}, Lorg/json/JSONObject;->optBoolean(Ljava/lang/String;)Z

    move-result v3

    if-eqz v3, :cond_2

    .line 173
    :cond_0
    const/4 v0, 0x0

    .line 192
    :cond_1
    return-object v0

    .line 175
    :cond_2
    const-string v3, "body"

    const-string v4, ""

    invoke-virtual {v2, v3, v4}, Lorg/json/JSONObject;->optString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    iput-object v3, v0, Lolive/app/OliveUpdater$Release;->notes:Ljava/lang/String;

    .line 176
    const-string v3, "html_url"

    const-string v4, ""

    invoke-virtual {v2, v3, v4}, Lorg/json/JSONObject;->optString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    iput-object v3, v0, Lolive/app/OliveUpdater$Release;->pageUrl:Ljava/lang/String;

    .line 177
    const-string v3, "assets"

    invoke-virtual {v2, v3}, Lorg/json/JSONObject;->optJSONArray(Ljava/lang/String;)Lorg/json/JSONArray;

    move-result-object v3

    move v2, v1

    .line 178
    :goto_0
    if-eqz v3, :cond_4

    invoke-virtual {v3}, Lorg/json/JSONArray;->length()I

    move-result v4

    if-ge v2, v4, :cond_4

    .line 179
    invoke-virtual {v3, v2}, Lorg/json/JSONArray;->getJSONObject(I)Lorg/json/JSONObject;

    move-result-object v4

    .line 180
    const-string v5, "name"

    invoke-virtual {v4, v5}, Lorg/json/JSONObject;->optString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    .line 181
    const-string v6, ".apk"

    invoke-virtual {v5, v6}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v6

    if-eqz v6, :cond_3

    .line 182
    iput-object v5, v0, Lolive/app/OliveUpdater$Release;->apkName:Ljava/lang/String;

    .line 183
    const-string v5, "browser_download_url"

    invoke-virtual {v4, v5}, Lorg/json/JSONObject;->optString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    iput-object v4, v0, Lolive/app/OliveUpdater$Release;->apkUrl:Ljava/lang/String;

    .line 178
    :cond_3
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 186
    :cond_4
    :goto_1
    if-eqz v3, :cond_1

    invoke-virtual {v3}, Lorg/json/JSONArray;->length()I

    move-result v2

    if-ge v1, v2, :cond_1

    .line 187
    invoke-virtual {v3, v1}, Lorg/json/JSONArray;->getJSONObject(I)Lorg/json/JSONObject;

    move-result-object v2

    .line 188
    iget-object v4, v0, Lolive/app/OliveUpdater$Release;->apkName:Ljava/lang/String;

    if-eqz v4, :cond_5

    const-string v4, "name"

    invoke-virtual {v2, v4}, Lorg/json/JSONObject;->optString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v6, v0, Lolive/app/OliveUpdater$Release;->apkName:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ".sha256"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_5

    .line 189
    const-string v4, "browser_download_url"

    invoke-virtual {v2, v4}, Lorg/json/JSONObject;->optString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    iput-object v2, v0, Lolive/app/OliveUpdater$Release;->shaUrl:Ljava/lang/String;

    .line 186
    :cond_5
    add-int/lit8 v1, v1, 0x1

    goto :goto_1
.end method

.method private static readAll(Ljava/io/InputStream;)Ljava/lang/String;
    .locals 4
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    .line 416
    new-instance v0, Ljava/io/ByteArrayOutputStream;

    invoke-direct {v0}, Ljava/io/ByteArrayOutputStream;-><init>()V

    .line 417
    const/16 v1, 0x2000

    new-array v1, v1, [B

    .line 419
    :goto_0
    invoke-virtual {p0, v1}, Ljava/io/InputStream;->read([B)I

    move-result v2

    if-lez v2, :cond_0

    .line 420
    const/4 v3, 0x0

    invoke-virtual {v0, v1, v3, v2}, Ljava/io/ByteArrayOutputStream;->write([BII)V

    goto :goto_0

    .line 422
    :cond_0
    invoke-virtual {p0}, Ljava/io/InputStream;->close()V

    .line 423
    const-string v1, "UTF-8"

    invoke-virtual {v0, v1}, Ljava/io/ByteArrayOutputStream;->toString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method private static readUrl(Ljava/lang/String;)Ljava/lang/String;
    .locals 3
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    const/16 v1, 0x1f40

    .line 404
    new-instance v0, Ljava/net/URL;

    invoke-direct {v0, p0}, Ljava/net/URL;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/net/URL;->openConnection()Ljava/net/URLConnection;

    move-result-object v0

    check-cast v0, Ljava/net/HttpURLConnection;

    .line 405
    invoke-virtual {v0, v1}, Ljava/net/HttpURLConnection;->setConnectTimeout(I)V

    .line 406
    invoke-virtual {v0, v1}, Ljava/net/HttpURLConnection;->setReadTimeout(I)V

    .line 407
    const-string v1, "User-Agent"

    const-string v2, "OliveApp-modern"

    invoke-virtual {v0, v1, v2}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 409
    :try_start_0
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->getInputStream()Ljava/io/InputStream;

    move-result-object v1

    invoke-static {v1}, Lolive/app/OliveUpdater;->readAll(Ljava/io/InputStream;)Ljava/lang/String;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    move-result-object v1

    .line 411
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 409
    return-object v1

    .line 411
    :catchall_0
    move-exception v1

    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 412
    throw v1
.end method

.method private static sameSigner([Landroid/content/pm/Signature;[Landroid/content/pm/Signature;)Z
    .locals 4

    .prologue
    const/4 v1, 0x0

    .line 350
    if-eqz p0, :cond_0

    if-eqz p1, :cond_0

    array-length v0, p0

    if-eqz v0, :cond_0

    array-length v0, p0

    array-length v2, p1

    if-eq v0, v2, :cond_1

    .line 358
    :cond_0
    :goto_0
    return v1

    :cond_1
    move v0, v1

    .line 353
    :goto_1
    array-length v2, p0

    if-ge v0, v2, :cond_2

    .line 354
    aget-object v2, p0, v0

    aget-object v3, p1, v0

    invoke-virtual {v2, v3}, Landroid/content/pm/Signature;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    .line 353
    add-int/lit8 v0, v0, 0x1

    goto :goto_1

    .line 358
    :cond_2
    const/4 v1, 0x1

    goto :goto_0
.end method

.method static sha256(Ljava/io/File;)Ljava/lang/String;
    .locals 8
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    const/4 v1, 0x0

    .line 362
    const-string v0, "SHA-256"

    invoke-static {v0}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object v0

    .line 363
    new-instance v2, Ljava/io/FileInputStream;

    invoke-direct {v2, p0}, Ljava/io/FileInputStream;-><init>(Ljava/io/File;)V

    .line 364
    const/high16 v3, 0x10000

    new-array v3, v3, [B

    .line 366
    :goto_0
    invoke-virtual {v2, v3}, Ljava/io/InputStream;->read([B)I

    move-result v4

    if-lez v4, :cond_0

    .line 367
    invoke-virtual {v0, v3, v1, v4}, Ljava/security/MessageDigest;->update([BII)V

    goto :goto_0

    .line 369
    :cond_0
    invoke-virtual {v2}, Ljava/io/InputStream;->close()V

    .line 370
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    .line 371
    invoke-virtual {v0}, Ljava/security/MessageDigest;->digest()[B

    move-result-object v3

    array-length v4, v3

    move v0, v1

    :goto_1
    if-ge v0, v4, :cond_1

    aget-byte v5, v3, v0

    .line 372
    const-string v6, "%02x"

    const/4 v7, 0x1

    new-array v7, v7, [Ljava/lang/Object;

    invoke-static {v5}, Ljava/lang/Byte;->valueOf(B)Ljava/lang/Byte;

    move-result-object v5

    aput-object v5, v7, v1

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v2, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 371
    add-int/lit8 v0, v0, 0x1

    goto :goto_1

    .line 374
    :cond_1
    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method private static signers(Landroid/content/pm/PackageInfo;)[Landroid/content/pm/Signature;
    .locals 2

    .prologue
    .line 343
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1c

    if-lt v0, v1, :cond_0

    iget-object v0, p0, Landroid/content/pm/PackageInfo;->signingInfo:Landroid/content/pm/SigningInfo;

    if-eqz v0, :cond_0

    .line 344
    iget-object v0, p0, Landroid/content/pm/PackageInfo;->signingInfo:Landroid/content/pm/SigningInfo;

    invoke-virtual {v0}, Landroid/content/pm/SigningInfo;->getApkContentsSigners()[Landroid/content/pm/Signature;

    move-result-object v0

    .line 346
    :goto_0
    return-object v0

    :cond_0
    iget-object v0, p0, Landroid/content/pm/PackageInfo;->signatures:[Landroid/content/pm/Signature;

    goto :goto_0
.end method

.method private static startUpdate(Landroid/app/Activity;Lolive/app/OliveUpdater$Release;)V
    .locals 7

    .prologue
    const/4 v3, 0x0

    const/4 v5, 0x0

    .line 220
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1a

    if-lt v0, v1, :cond_0

    invoke-virtual {p0}, Landroid/app/Activity;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/pm/PackageManager;->canRequestPackageInstalls()Z

    move-result v0

    if-nez v0, :cond_0

    .line 221
    new-instance v0, Landroid/app/AlertDialog$Builder;

    invoke-direct {v0, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    const-string v1, "Allow updates"

    .line 222
    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const-string v1, "To install updates, allow Olive to install apps. Turn on \"Allow from this source\", then come back and tap Update again."

    .line 223
    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const-string v1, "Open settings"

    new-instance v2, Lolive/app/OliveUpdater$4;

    invoke-direct {v2, p0}, Lolive/app/OliveUpdater$4;-><init>(Landroid/app/Activity;)V

    .line 224
    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const-string v1, "Cancel"

    .line 230
    invoke-virtual {v0, v1, v3}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    .line 231
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->show()Landroid/app/AlertDialog;

    .line 283
    :goto_0
    return-void

    .line 234
    :cond_0
    new-instance v0, Landroid/widget/LinearLayout;

    invoke-direct {v0, p0}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 235
    const/high16 v1, 0x41c00000    # 24.0f

    invoke-virtual {p0}, Landroid/app/Activity;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v2

    iget v2, v2, Landroid/util/DisplayMetrics;->density:F

    mul-float/2addr v1, v2

    invoke-static {v1}, Ljava/lang/Math;->round(F)I

    move-result v1

    .line 236
    div-int/lit8 v2, v1, 0x2

    invoke-virtual {v0, v1, v2, v1, v5}, Landroid/widget/LinearLayout;->setPadding(IIII)V

    .line 237
    new-instance v4, Landroid/widget/ProgressBar;

    const v1, 0x1010078

    invoke-direct {v4, p0, v3, v1}, Landroid/widget/ProgressBar;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    .line 238
    const/16 v1, 0x64

    invoke-virtual {v4, v1}, Landroid/widget/ProgressBar;->setMax(I)V

    .line 239
    new-instance v1, Landroid/widget/LinearLayout$LayoutParams;

    const/4 v2, -0x1

    const/4 v3, -0x2

    invoke-direct {v1, v2, v3}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    invoke-virtual {v0, v4, v1}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 240
    new-instance v1, Landroid/app/AlertDialog$Builder;

    invoke-direct {v1, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Downloading "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-object v3, p1, Lolive/app/OliveUpdater$Release;->version:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "\u2026"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 241
    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 242
    invoke-virtual {v1, v0}, Landroid/app/AlertDialog$Builder;->setView(Landroid/view/View;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    .line 243
    invoke-virtual {v0, v5}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    .line 244
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->show()Landroid/app/AlertDialog;

    move-result-object v5

    .line 245
    new-instance v3, Landroid/os/Handler;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v0

    invoke-direct {v3, v0}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    .line 246
    new-instance v6, Ljava/lang/Thread;

    new-instance v0, Lolive/app/OliveUpdater$5;

    move-object v1, p0

    move-object v2, p1

    invoke-direct/range {v0 .. v5}, Lolive/app/OliveUpdater$5;-><init>(Landroid/app/Activity;Lolive/app/OliveUpdater$Release;Landroid/os/Handler;Landroid/widget/ProgressBar;Landroid/app/AlertDialog;)V

    invoke-direct {v6, v0}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;)V

    .line 282
    invoke-virtual {v6}, Ljava/lang/Thread;->start()V

    goto :goto_0
.end method

.method static verify(Landroid/content/Context;Ljava/io/File;Lolive/app/OliveUpdater$Release;)Ljava/lang/String;
    .locals 4
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    .line 321
    iget-object v0, p2, Lolive/app/OliveUpdater$Release;->shaUrl:Ljava/lang/String;

    if-nez v0, :cond_0

    .line 322
    const-string v0, "The release has no checksum, so the download can\'t be verified."

    .line 338
    :goto_0
    return-object v0

    .line 324
    :cond_0
    iget-object v0, p2, Lolive/app/OliveUpdater$Release;->shaUrl:Ljava/lang/String;

    invoke-static {v0}, Lolive/app/OliveUpdater;->readUrl(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    const-string v1, "\\s+"

    invoke-virtual {v0, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v0

    const/4 v1, 0x0

    aget-object v0, v0, v1

    invoke-virtual {v0}, Ljava/lang/String;->toLowerCase()Ljava/lang/String;

    move-result-object v0

    .line 325
    invoke-static {p1}, Lolive/app/OliveUpdater;->sha256(Ljava/io/File;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_1

    .line 326
    const-string v0, "The download didn\'t match its published checksum. Try again later."

    goto :goto_0

    .line 328
    :cond_1
    invoke-virtual {p0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v1

    .line 329
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v2, 0x1c

    if-lt v0, v2, :cond_3

    const/high16 v0, 0x8000000

    .line 330
    :goto_1
    invoke-virtual {p1}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2, v0}, Landroid/content/pm/PackageManager;->getPackageArchiveInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object v2

    .line 331
    invoke-virtual {p0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, v3, v0}, Landroid/content/pm/PackageManager;->getPackageInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object v0

    .line 332
    if-eqz v2, :cond_2

    invoke-virtual {p0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v1

    iget-object v3, v2, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    invoke-virtual {v1, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_4

    .line 333
    :cond_2
    const-string v0, "The download isn\'t an Olive app update."

    goto :goto_0

    .line 329
    :cond_3
    const/16 v0, 0x40

    goto :goto_1

    .line 335
    :cond_4
    invoke-static {v2}, Lolive/app/OliveUpdater;->signers(Landroid/content/pm/PackageInfo;)[Landroid/content/pm/Signature;

    move-result-object v1

    invoke-static {v0}, Lolive/app/OliveUpdater;->signers(Landroid/content/pm/PackageInfo;)[Landroid/content/pm/Signature;

    move-result-object v0

    invoke-static {v1, v0}, Lolive/app/OliveUpdater;->sameSigner([Landroid/content/pm/Signature;[Landroid/content/pm/Signature;)Z

    move-result v0

    if-nez v0, :cond_5

    .line 336
    const-string v0, "The update isn\'t signed with the same key as this app, so it wasn\'t installed."

    goto :goto_0

    .line 338
    :cond_5
    const/4 v0, 0x0

    goto :goto_0
.end method
