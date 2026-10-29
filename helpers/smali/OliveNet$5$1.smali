.class Lolive/app/OliveNet$5$1;
.super Ljava/lang/Object;
.source "OliveNet.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveNet$5;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lolive/app/OliveNet$5;


# direct methods
.method constructor <init>(Lolive/app/OliveNet$5;)V
    .locals 0

    .prologue
    .line 241
    iput-object p1, p0, Lolive/app/OliveNet$5$1;->this$0:Lolive/app/OliveNet$5;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    .prologue
    .line 243
    iget-object v0, p0, Lolive/app/OliveNet$5$1;->this$0:Lolive/app/OliveNet$5;

    iget-object v0, v0, Lolive/app/OliveNet$5;->val$found:Ljava/util/concurrent/atomic/AtomicInteger;

    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicInteger;->get()I

    move-result v0

    .line 244
    if-lez v0, :cond_0

    iget-object v1, p0, Lolive/app/OliveNet$5$1;->this$0:Lolive/app/OliveNet$5;

    iget-boolean v1, v1, Lolive/app/OliveNet$5;->val$remember:Z

    if-eqz v1, :cond_0

    .line 245
    iget-object v1, p0, Lolive/app/OliveNet$5$1;->this$0:Lolive/app/OliveNet$5;

    iget-object v1, v1, Lolive/app/OliveNet$5;->val$picker:Landroid/app/Activity;

    iget-object v2, p0, Lolive/app/OliveNet$5$1;->this$0:Lolive/app/OliveNet$5;

    iget-object v2, v2, Lolive/app/OliveNet$5;->val$subnets:Ljava/util/List;

    invoke-static {v1, v2}, Lolive/app/OliveNet;->access$100(Landroid/app/Activity;Ljava/util/List;)V

    .line 247
    :cond_0
    iget-object v1, p0, Lolive/app/OliveNet$5$1;->this$0:Lolive/app/OliveNet$5;

    iget-object v1, v1, Lolive/app/OliveNet$5;->val$picker:Landroid/app/Activity;

    if-lez v0, :cond_2

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Found "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " Olive"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const/4 v3, 0x1

    if-ne v0, v3, :cond_1

    const-string v0, ""

    :goto_0
    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, " on "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v2, p0, Lolive/app/OliveNet$5$1;->this$0:Lolive/app/OliveNet$5;

    iget-object v2, v2, Lolive/app/OliveNet$5;->val$label:Ljava/lang/String;

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, ". Tap one to connect."

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 248
    :goto_1
    const/4 v2, 0x0

    .line 247
    invoke-static {v1, v0, v2}, Lolive/app/OliveNet;->access$200(Landroid/app/Activity;Ljava/lang/String;Z)V

    .line 249
    return-void

    .line 247
    :cond_1
    const-string v0, "s"

    goto :goto_0

    .line 248
    :cond_2
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "No Olive found on "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v2, p0, Lolive/app/OliveNet$5$1;->this$0:Lolive/app/OliveNet$5;

    iget-object v2, v2, Lolive/app/OliveNet$5;->val$label:Ljava/lang/String;

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, ". Check the network, or enter the address above."

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    goto :goto_1
.end method
