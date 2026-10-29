.class Lolive/app/OliveNet$4;
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

.field final synthetic val$host:Ljava/lang/String;

.field final synthetic val$main:Landroid/os/Handler;

.field final synthetic val$picker:Landroid/app/Activity;


# direct methods
.method constructor <init>(Ljava/lang/String;Ljava/util/concurrent/atomic/AtomicInteger;Landroid/os/Handler;Landroid/app/Activity;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 220
    iput-object p1, p0, Lolive/app/OliveNet$4;->val$host:Ljava/lang/String;

    iput-object p2, p0, Lolive/app/OliveNet$4;->val$found:Ljava/util/concurrent/atomic/AtomicInteger;

    iput-object p3, p0, Lolive/app/OliveNet$4;->val$main:Landroid/os/Handler;

    iput-object p4, p0, Lolive/app/OliveNet$4;->val$picker:Landroid/app/Activity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    .prologue
    .line 222
    iget-object v0, p0, Lolive/app/OliveNet$4;->val$host:Ljava/lang/String;

    const/16 v1, 0x50

    const/16 v2, 0x76c

    const/16 v3, 0x190

    invoke-static {v0, v1, v2, v3}, Lolive/app/OliveNet;->probe(Ljava/lang/String;III)Ljava/lang/String;

    move-result-object v0

    .line 223
    if-eqz v0, :cond_0

    .line 224
    iget-object v1, p0, Lolive/app/OliveNet$4;->val$found:Ljava/util/concurrent/atomic/AtomicInteger;

    invoke-virtual {v1}, Ljava/util/concurrent/atomic/AtomicInteger;->incrementAndGet()I

    .line 225
    iget-object v1, p0, Lolive/app/OliveNet$4;->val$main:Landroid/os/Handler;

    new-instance v2, Lolive/app/OliveNet$4$1;

    invoke-direct {v2, p0, v0}, Lolive/app/OliveNet$4$1;-><init>(Lolive/app/OliveNet$4;Ljava/lang/String;)V

    invoke-virtual {v1, v2}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 231
    :cond_0
    return-void
.end method
