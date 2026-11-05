.class Lolive/app/OliveUpdater$5;
.super Ljava/lang/Object;
.source "OliveUpdater.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveUpdater;->startUpdate(Landroid/app/Activity;Lolive/app/OliveUpdater$Release;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$activity:Landroid/app/Activity;

.field final synthetic val$bar:Landroid/widget/ProgressBar;

.field final synthetic val$main:Landroid/os/Handler;

.field final synthetic val$progress:Landroid/app/AlertDialog;

.field final synthetic val$release:Lolive/app/OliveUpdater$Release;


# direct methods
.method constructor <init>(Landroid/app/Activity;Lolive/app/OliveUpdater$Release;Landroid/os/Handler;Landroid/widget/ProgressBar;Landroid/app/AlertDialog;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 246
    iput-object p1, p0, Lolive/app/OliveUpdater$5;->val$activity:Landroid/app/Activity;

    iput-object p2, p0, Lolive/app/OliveUpdater$5;->val$release:Lolive/app/OliveUpdater$Release;

    iput-object p3, p0, Lolive/app/OliveUpdater$5;->val$main:Landroid/os/Handler;

    iput-object p4, p0, Lolive/app/OliveUpdater$5;->val$bar:Landroid/widget/ProgressBar;

    iput-object p5, p0, Lolive/app/OliveUpdater$5;->val$progress:Landroid/app/AlertDialog;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    .prologue
    .line 248
    .line 249
    new-instance v1, Ljava/io/File;

    new-instance v0, Ljava/io/File;

    iget-object v2, p0, Lolive/app/OliveUpdater$5;->val$activity:Landroid/app/Activity;

    invoke-virtual {v2}, Landroid/app/Activity;->getCacheDir()Ljava/io/File;

    move-result-object v2

    const-string v3, "updates"

    invoke-direct {v0, v2, v3}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    iget-object v2, p0, Lolive/app/OliveUpdater$5;->val$release:Lolive/app/OliveUpdater$Release;

    iget-object v2, v2, Lolive/app/OliveUpdater$Release;->apkName:Ljava/lang/String;

    invoke-direct {v1, v0, v2}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 251
    :try_start_0
    invoke-virtual {v1}, Ljava/io/File;->getParentFile()Ljava/io/File;

    move-result-object v0

    invoke-virtual {v0}, Ljava/io/File;->mkdirs()Z

    .line 252
    iget-object v0, p0, Lolive/app/OliveUpdater$5;->val$release:Lolive/app/OliveUpdater$Release;

    iget-object v0, v0, Lolive/app/OliveUpdater$Release;->apkUrl:Ljava/lang/String;

    new-instance v2, Lolive/app/OliveUpdater$5$1;

    invoke-direct {v2, p0}, Lolive/app/OliveUpdater$5$1;-><init>(Lolive/app/OliveUpdater$5;)V

    invoke-static {v0, v1, v2}, Lolive/app/OliveUpdater;->download(Ljava/lang/String;Ljava/io/File;Lolive/app/OliveUpdater$Progress;)V

    .line 261
    iget-object v0, p0, Lolive/app/OliveUpdater$5;->val$activity:Landroid/app/Activity;

    iget-object v2, p0, Lolive/app/OliveUpdater$5;->val$release:Lolive/app/OliveUpdater$Release;

    invoke-static {v0, v1, v2}, Lolive/app/OliveUpdater;->verify(Landroid/content/Context;Ljava/io/File;Lolive/app/OliveUpdater$Release;)Ljava/lang/String;

    move-result-object v0

    .line 262
    if-nez v0, :cond_0

    .line 263
    iget-object v2, p0, Lolive/app/OliveUpdater$5;->val$activity:Landroid/app/Activity;

    invoke-static {v2, v1}, Lolive/app/OliveUpdater;->install(Landroid/content/Context;Ljava/io/File;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 269
    :cond_0
    :goto_0
    iget-object v1, p0, Lolive/app/OliveUpdater$5;->val$main:Landroid/os/Handler;

    new-instance v2, Lolive/app/OliveUpdater$5$2;

    invoke-direct {v2, p0, v0}, Lolive/app/OliveUpdater$5$2;-><init>(Lolive/app/OliveUpdater$5;Ljava/lang/String;)V

    invoke-virtual {v1, v2}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 281
    return-void

    .line 265
    :catch_0
    move-exception v0

    .line 266
    const-string v0, "The download failed. Check your connection and try again."

    goto :goto_0
.end method
