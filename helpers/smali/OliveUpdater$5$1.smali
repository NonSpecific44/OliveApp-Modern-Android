.class Lolive/app/OliveUpdater$5$1;
.super Ljava/lang/Object;
.source "OliveUpdater.java"

# interfaces
.implements Lolive/app/OliveUpdater$Progress;


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


# direct methods
.method constructor <init>(Lolive/app/OliveUpdater$5;)V
    .locals 0

    .prologue
    .line 252
    iput-object p1, p0, Lolive/app/OliveUpdater$5$1;->this$0:Lolive/app/OliveUpdater$5;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public update(I)V
    .locals 2

    .prologue
    .line 254
    iget-object v0, p0, Lolive/app/OliveUpdater$5$1;->this$0:Lolive/app/OliveUpdater$5;

    iget-object v0, v0, Lolive/app/OliveUpdater$5;->val$main:Landroid/os/Handler;

    new-instance v1, Lolive/app/OliveUpdater$5$1$1;

    invoke-direct {v1, p0, p1}, Lolive/app/OliveUpdater$5$1$1;-><init>(Lolive/app/OliveUpdater$5$1;I)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 259
    return-void
.end method
