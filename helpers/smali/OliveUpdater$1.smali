.class Lolive/app/OliveUpdater$1;
.super Ljava/lang/Object;
.source "OliveUpdater.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveUpdater;->check(Landroid/app/Activity;Z)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$activity:Landroid/app/Activity;

.field final synthetic val$main:Landroid/os/Handler;

.field final synthetic val$manual:Z


# direct methods
.method constructor <init>(Landroid/os/Handler;Landroid/app/Activity;Z)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 118
    iput-object p1, p0, Lolive/app/OliveUpdater$1;->val$main:Landroid/os/Handler;

    iput-object p2, p0, Lolive/app/OliveUpdater$1;->val$activity:Landroid/app/Activity;

    iput-boolean p3, p0, Lolive/app/OliveUpdater$1;->val$manual:Z

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .prologue
    .line 120
    invoke-static {}, Lolive/app/OliveUpdater;->fetchLatest()Lolive/app/OliveUpdater$Release;

    move-result-object v0

    .line 121
    iget-object v1, p0, Lolive/app/OliveUpdater$1;->val$main:Landroid/os/Handler;

    new-instance v2, Lolive/app/OliveUpdater$1$1;

    invoke-direct {v2, p0, v0}, Lolive/app/OliveUpdater$1$1;-><init>(Lolive/app/OliveUpdater$1;Lolive/app/OliveUpdater$Release;)V

    invoke-virtual {v1, v2}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 142
    return-void
.end method
