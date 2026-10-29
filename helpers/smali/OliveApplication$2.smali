.class Lolive/app/OliveApplication$2;
.super Ljava/lang/Object;
.source "OliveApplication.java"

# interfaces
.implements Landroid/app/Application$ActivityLifecycleCallbacks;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveApplication;->onCreate()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lolive/app/OliveApplication;


# direct methods
.method constructor <init>(Lolive/app/OliveApplication;)V
    .locals 0

    .prologue
    .line 57
    iput-object p1, p0, Lolive/app/OliveApplication$2;->this$0:Lolive/app/OliveApplication;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onActivityCreated(Landroid/app/Activity;Landroid/os/Bundle;)V
    .locals 0

    .prologue
    .line 67
    return-void
.end method

.method public onActivityDestroyed(Landroid/app/Activity;)V
    .locals 0

    .prologue
    .line 82
    return-void
.end method

.method public onActivityPaused(Landroid/app/Activity;)V
    .locals 0

    .prologue
    .line 73
    return-void
.end method

.method public onActivityResumed(Landroid/app/Activity;)V
    .locals 2

    .prologue
    .line 59
    invoke-static {p1}, Lolive/app/Compat;->animateLists(Landroid/app/Activity;)V

    .line 60
    iget-object v0, p0, Lolive/app/OliveApplication$2;->this$0:Lolive/app/OliveApplication;

    invoke-static {v0}, Lolive/app/OliveApplication;->access$100(Lolive/app/OliveApplication;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 61
    iget-object v0, p0, Lolive/app/OliveApplication$2;->this$0:Lolive/app/OliveApplication;

    const/4 v1, 0x1

    invoke-static {v0, v1}, Lolive/app/OliveApplication;->access$102(Lolive/app/OliveApplication;Z)Z

    .line 62
    iget-object v0, p0, Lolive/app/OliveApplication$2;->this$0:Lolive/app/OliveApplication;

    invoke-static {v0, p1}, Lolive/app/OliveApplication;->access$200(Lolive/app/OliveApplication;Landroid/app/Activity;)V

    .line 64
    :cond_0
    return-void
.end method

.method public onActivitySaveInstanceState(Landroid/app/Activity;Landroid/os/Bundle;)V
    .locals 0

    .prologue
    .line 79
    return-void
.end method

.method public onActivityStarted(Landroid/app/Activity;)V
    .locals 0

    .prologue
    .line 70
    return-void
.end method

.method public onActivityStopped(Landroid/app/Activity;)V
    .locals 0

    .prologue
    .line 76
    return-void
.end method
