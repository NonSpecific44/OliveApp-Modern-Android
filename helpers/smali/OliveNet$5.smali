.class Lolive/app/OliveNet$5;
.super Ljava/lang/Object;
.source "OliveNet.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveNet;->scan(Landroid/app/Activity;Ljava/util/List;Z)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$found:Ljava/util/concurrent/atomic/AtomicInteger;

.field final synthetic val$label:Ljava/lang/String;

.field final synthetic val$main:Landroid/os/Handler;

.field final synthetic val$picker:Landroid/app/Activity;

.field final synthetic val$pool:Ljava/util/concurrent/ExecutorService;

.field final synthetic val$remember:Z

.field final synthetic val$subnets:Ljava/util/List;


# direct methods
.method constructor <init>(Ljava/util/concurrent/ExecutorService;Landroid/os/Handler;Ljava/util/concurrent/atomic/AtomicInteger;ZLandroid/app/Activity;Ljava/util/List;Ljava/lang/String;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 235
    iput-object p1, p0, Lolive/app/OliveNet$5;->val$pool:Ljava/util/concurrent/ExecutorService;

    iput-object p2, p0, Lolive/app/OliveNet$5;->val$main:Landroid/os/Handler;

    iput-object p3, p0, Lolive/app/OliveNet$5;->val$found:Ljava/util/concurrent/atomic/AtomicInteger;

    iput-boolean p4, p0, Lolive/app/OliveNet$5;->val$remember:Z

    iput-object p5, p0, Lolive/app/OliveNet$5;->val$picker:Landroid/app/Activity;

    iput-object p6, p0, Lolive/app/OliveNet$5;->val$subnets:Ljava/util/List;

    iput-object p7, p0, Lolive/app/OliveNet$5;->val$label:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    .prologue
    .line 238
    :try_start_0
    iget-object v0, p0, Lolive/app/OliveNet$5;->val$pool:Ljava/util/concurrent/ExecutorService;

    const-wide/16 v2, 0x2

    sget-object v1, Ljava/util/concurrent/TimeUnit;->MINUTES:Ljava/util/concurrent/TimeUnit;

    invoke-interface {v0, v2, v3, v1}, Ljava/util/concurrent/ExecutorService;->awaitTermination(JLjava/util/concurrent/TimeUnit;)Z
    :try_end_0
    .catch Ljava/lang/InterruptedException; {:try_start_0 .. :try_end_0} :catch_0

    .line 241
    :goto_0
    iget-object v0, p0, Lolive/app/OliveNet$5;->val$main:Landroid/os/Handler;

    new-instance v1, Lolive/app/OliveNet$5$1;

    invoke-direct {v1, p0}, Lolive/app/OliveNet$5$1;-><init>(Lolive/app/OliveNet$5;)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 251
    return-void

    .line 239
    :catch_0
    move-exception v0

    goto :goto_0
.end method
