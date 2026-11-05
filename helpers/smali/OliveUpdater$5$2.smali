.class Lolive/app/OliveUpdater$5$2;
.super Ljava/lang/Object;
.source "OliveUpdater.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveUpdater$5;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lolive/app/OliveUpdater$5;

.field final synthetic val$message:Ljava/lang/String;


# direct methods
.method constructor <init>(Lolive/app/OliveUpdater$5;Ljava/lang/String;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 269
    iput-object p1, p0, Lolive/app/OliveUpdater$5$2;->this$0:Lolive/app/OliveUpdater$5;

    iput-object p2, p0, Lolive/app/OliveUpdater$5$2;->val$message:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .prologue
    .line 272
    :try_start_0
    iget-object v0, p0, Lolive/app/OliveUpdater$5$2;->this$0:Lolive/app/OliveUpdater$5;

    iget-object v0, v0, Lolive/app/OliveUpdater$5;->val$progress:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->dismiss()V
    :try_end_0
    .catch Ljava/lang/RuntimeException; {:try_start_0 .. :try_end_0} :catch_0

    .line 275
    :goto_0
    iget-object v0, p0, Lolive/app/OliveUpdater$5$2;->val$message:Ljava/lang/String;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lolive/app/OliveUpdater$5$2;->this$0:Lolive/app/OliveUpdater$5;

    iget-object v0, v0, Lolive/app/OliveUpdater$5;->val$activity:Landroid/app/Activity;

    invoke-virtual {v0}, Landroid/app/Activity;->isFinishing()Z

    move-result v0

    if-nez v0, :cond_0

    .line 276
    new-instance v0, Landroid/app/AlertDialog$Builder;

    iget-object v1, p0, Lolive/app/OliveUpdater$5$2;->this$0:Lolive/app/OliveUpdater$5;

    iget-object v1, v1, Lolive/app/OliveUpdater$5;->val$activity:Landroid/app/Activity;

    invoke-direct {v0, v1}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    const-string v1, "Update not installed"

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    iget-object v1, p0, Lolive/app/OliveUpdater$5$2;->val$message:Ljava/lang/String;

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const-string v1, "OK"

    const/4 v2, 0x0

    .line 277
    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->show()Landroid/app/AlertDialog;

    .line 279
    :cond_0
    return-void

    .line 273
    :catch_0
    move-exception v0

    goto :goto_0
.end method
