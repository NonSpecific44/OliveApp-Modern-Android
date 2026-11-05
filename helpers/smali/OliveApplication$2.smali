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
    .line 58
    iput-object p1, p0, Lolive/app/OliveApplication$2;->this$0:Lolive/app/OliveApplication;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onActivityCreated(Landroid/app/Activity;Landroid/os/Bundle;)V
    .locals 0

    .prologue
    .line 75
    invoke-static {p1}, Lolive/app/OliveUi;->orientationPolicy(Landroid/app/Activity;)V

    .line 76
    return-void
.end method

.method public onActivityDestroyed(Landroid/app/Activity;)V
    .locals 0

    .prologue
    .line 93
    return-void
.end method

.method public onActivityPaused(Landroid/app/Activity;)V
    .locals 3

    .prologue
    const/4 v0, 0x0

    .line 82
    iget-object v1, p0, Lolive/app/OliveApplication$2;->this$0:Lolive/app/OliveApplication;

    iget-object v2, p0, Lolive/app/OliveApplication$2;->this$0:Lolive/app/OliveApplication;

    invoke-static {v2}, Lolive/app/OliveApplication;->access$100(Lolive/app/OliveApplication;)I

    move-result v2

    add-int/lit8 v2, v2, -0x1

    invoke-static {v0, v2}, Ljava/lang/Math;->max(II)I

    move-result v2

    invoke-static {v1, v2}, Lolive/app/OliveApplication;->access$102(Lolive/app/OliveApplication;I)I

    .line 83
    iget-object v1, p0, Lolive/app/OliveApplication$2;->this$0:Lolive/app/OliveApplication;

    invoke-static {v1}, Lolive/app/OliveApplication;->access$100(Lolive/app/OliveApplication;)I

    move-result v1

    if-lez v1, :cond_0

    const/4 v0, 0x1

    :cond_0
    sput-boolean v0, Lolive/app/OliveMediaService;->appVisible:Z

    .line 84
    return-void
.end method

.method public onActivityResumed(Landroid/app/Activity;)V
    .locals 3

    .prologue
    const/4 v2, 0x1

    .line 60
    iget-object v0, p0, Lolive/app/OliveApplication$2;->this$0:Lolive/app/OliveApplication;

    invoke-static {v0}, Lolive/app/OliveApplication;->access$108(Lolive/app/OliveApplication;)I

    .line 61
    sput-boolean v2, Lolive/app/OliveMediaService;->appVisible:Z

    .line 62
    invoke-static {p1}, Lolive/app/OliveMediaService;->ensureRunning(Landroid/app/Activity;)V

    .line 63
    invoke-static {p1}, Lolive/app/OliveUi;->attach(Landroid/app/Activity;)V

    .line 64
    invoke-virtual {p1}, Landroid/app/Activity;->getParent()Landroid/app/Activity;

    move-result-object v0

    if-nez v0, :cond_0

    const-string v0, "olive.app.LoadTabs"

    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Class;->getName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 65
    invoke-static {p1}, Lolive/app/OliveUpdater;->checkOnLaunch(Landroid/app/Activity;)V

    .line 67
    :cond_0
    invoke-static {p1}, Lolive/app/Compat;->animateLists(Landroid/app/Activity;)V

    .line 68
    iget-object v0, p0, Lolive/app/OliveApplication$2;->this$0:Lolive/app/OliveApplication;

    invoke-static {v0}, Lolive/app/OliveApplication;->access$200(Lolive/app/OliveApplication;)Z

    move-result v0

    if-nez v0, :cond_1

    .line 69
    iget-object v0, p0, Lolive/app/OliveApplication$2;->this$0:Lolive/app/OliveApplication;

    invoke-static {v0, v2}, Lolive/app/OliveApplication;->access$202(Lolive/app/OliveApplication;Z)Z

    .line 70
    iget-object v0, p0, Lolive/app/OliveApplication$2;->this$0:Lolive/app/OliveApplication;

    invoke-static {v0, p1}, Lolive/app/OliveApplication;->access$300(Lolive/app/OliveApplication;Landroid/app/Activity;)V

    .line 72
    :cond_1
    return-void
.end method

.method public onActivitySaveInstanceState(Landroid/app/Activity;Landroid/os/Bundle;)V
    .locals 0

    .prologue
    .line 90
    return-void
.end method

.method public onActivityStarted(Landroid/app/Activity;)V
    .locals 0

    .prologue
    .line 79
    return-void
.end method

.method public onActivityStopped(Landroid/app/Activity;)V
    .locals 0

    .prologue
    .line 87
    return-void
.end method
