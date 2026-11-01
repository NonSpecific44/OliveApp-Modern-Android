.class Lolive/app/OliveUpdater$1$1;
.super Ljava/lang/Object;
.source "OliveUpdater.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveUpdater$1;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lolive/app/OliveUpdater$1;

.field final synthetic val$release:Lolive/app/OliveUpdater$Release;


# direct methods
.method constructor <init>(Lolive/app/OliveUpdater$1;Lolive/app/OliveUpdater$Release;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 121
    iput-object p1, p0, Lolive/app/OliveUpdater$1$1;->this$0:Lolive/app/OliveUpdater$1;

    iput-object p2, p0, Lolive/app/OliveUpdater$1$1;->val$release:Lolive/app/OliveUpdater$Release;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 5

    .prologue
    const/4 v4, 0x1

    const/4 v3, 0x0

    .line 123
    invoke-static {v3}, Lolive/app/OliveUpdater;->access$002(Z)Z

    .line 124
    iget-object v0, p0, Lolive/app/OliveUpdater$1$1;->this$0:Lolive/app/OliveUpdater$1;

    iget-object v0, v0, Lolive/app/OliveUpdater$1;->val$activity:Landroid/app/Activity;

    invoke-virtual {v0}, Landroid/app/Activity;->isFinishing()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 140
    :cond_0
    :goto_0
    return-void

    .line 127
    :cond_1
    iget-object v0, p0, Lolive/app/OliveUpdater$1$1;->this$0:Lolive/app/OliveUpdater$1;

    iget-object v0, v0, Lolive/app/OliveUpdater$1;->val$activity:Landroid/app/Activity;

    invoke-static {v0}, Lolive/app/OliveUpdater;->installedVersion(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v0

    .line 128
    iget-object v1, p0, Lolive/app/OliveUpdater$1$1;->val$release:Lolive/app/OliveUpdater$Release;

    if-nez v1, :cond_2

    .line 129
    iget-object v0, p0, Lolive/app/OliveUpdater$1$1;->this$0:Lolive/app/OliveUpdater$1;

    iget-boolean v0, v0, Lolive/app/OliveUpdater$1;->val$manual:Z

    if-eqz v0, :cond_0

    .line 130
    iget-object v0, p0, Lolive/app/OliveUpdater$1$1;->this$0:Lolive/app/OliveUpdater$1;

    iget-object v0, v0, Lolive/app/OliveUpdater$1;->val$activity:Landroid/app/Activity;

    const-string v1, "Couldn\'t check for updates. Check your connection."

    invoke-static {v0, v1, v4}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    goto :goto_0

    .line 132
    :cond_2
    iget-object v1, p0, Lolive/app/OliveUpdater$1$1;->val$release:Lolive/app/OliveUpdater$Release;

    iget-object v1, v1, Lolive/app/OliveUpdater$Release;->version:Ljava/lang/String;

    invoke-static {v1, v0}, Lolive/app/OliveUpdater;->compare(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    if-lez v1, :cond_4

    iget-object v1, p0, Lolive/app/OliveUpdater$1$1;->val$release:Lolive/app/OliveUpdater$Release;

    iget-object v1, v1, Lolive/app/OliveUpdater$Release;->apkUrl:Ljava/lang/String;

    if-eqz v1, :cond_4

    .line 133
    iget-object v1, p0, Lolive/app/OliveUpdater$1$1;->this$0:Lolive/app/OliveUpdater$1;

    iget-object v1, v1, Lolive/app/OliveUpdater$1;->val$activity:Landroid/app/Activity;

    const-string v2, "Olive_Settings"

    invoke-virtual {v1, v2, v3}, Landroid/app/Activity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v1

    const-string v2, "update_skipped"

    const-string v3, ""

    invoke-interface {v1, v2, v3}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 134
    iget-object v2, p0, Lolive/app/OliveUpdater$1$1;->this$0:Lolive/app/OliveUpdater$1;

    iget-boolean v2, v2, Lolive/app/OliveUpdater$1;->val$manual:Z

    if-nez v2, :cond_3

    iget-object v2, p0, Lolive/app/OliveUpdater$1$1;->val$release:Lolive/app/OliveUpdater$Release;

    iget-object v2, v2, Lolive/app/OliveUpdater$Release;->version:Ljava/lang/String;

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_0

    .line 135
    :cond_3
    iget-object v1, p0, Lolive/app/OliveUpdater$1$1;->this$0:Lolive/app/OliveUpdater$1;

    iget-object v1, v1, Lolive/app/OliveUpdater$1;->val$activity:Landroid/app/Activity;

    iget-object v2, p0, Lolive/app/OliveUpdater$1$1;->val$release:Lolive/app/OliveUpdater$Release;

    invoke-static {v1, v2, v0}, Lolive/app/OliveUpdater;->access$100(Landroid/app/Activity;Lolive/app/OliveUpdater$Release;Ljava/lang/String;)V

    goto :goto_0

    .line 137
    :cond_4
    iget-object v1, p0, Lolive/app/OliveUpdater$1$1;->this$0:Lolive/app/OliveUpdater$1;

    iget-boolean v1, v1, Lolive/app/OliveUpdater$1;->val$manual:Z

    if-eqz v1, :cond_0

    .line 138
    iget-object v1, p0, Lolive/app/OliveUpdater$1$1;->this$0:Lolive/app/OliveUpdater$1;

    iget-object v1, v1, Lolive/app/OliveUpdater$1;->val$activity:Landroid/app/Activity;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "You have the latest version ("

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, ")."

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0, v4}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    goto/16 :goto_0
.end method
