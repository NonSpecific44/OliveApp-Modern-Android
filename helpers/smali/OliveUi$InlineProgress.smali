.class final Lolive/app/OliveUi$InlineProgress;
.super Landroid/app/ProgressDialog;
.source "OliveUi.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lolive/app/OliveUi;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x18
    name = "InlineProgress"
.end annotation


# instance fields
.field private final pull:Lolive/app/OliveUi$Pull;


# direct methods
.method constructor <init>(Landroid/content/Context;Lolive/app/OliveUi$Pull;)V
    .locals 0

    .prologue
    .line 86
    invoke-direct {p0, p1}, Landroid/app/ProgressDialog;-><init>(Landroid/content/Context;)V

    .line 87
    iput-object p2, p0, Lolive/app/OliveUi$InlineProgress;->pull:Lolive/app/OliveUi$Pull;

    .line 88
    return-void
.end method


# virtual methods
.method public cancel()V
    .locals 1

    .prologue
    .line 107
    iget-object v0, p0, Lolive/app/OliveUi$InlineProgress;->pull:Lolive/app/OliveUi$Pull;

    invoke-virtual {v0}, Lolive/app/OliveUi$Pull;->finishLoading()V

    .line 108
    return-void
.end method

.method public dismiss()V
    .locals 1

    .prologue
    .line 102
    iget-object v0, p0, Lolive/app/OliveUi$InlineProgress;->pull:Lolive/app/OliveUi$Pull;

    invoke-virtual {v0}, Lolive/app/OliveUi$Pull;->finishLoading()V

    .line 103
    return-void
.end method

.method public hide()V
    .locals 1

    .prologue
    .line 112
    iget-object v0, p0, Lolive/app/OliveUi$InlineProgress;->pull:Lolive/app/OliveUi$Pull;

    invoke-virtual {v0}, Lolive/app/OliveUi$Pull;->finishLoading()V

    .line 113
    return-void
.end method

.method public isShowing()Z
    .locals 1

    .prologue
    .line 92
    iget-object v0, p0, Lolive/app/OliveUi$InlineProgress;->pull:Lolive/app/OliveUi$Pull;

    iget-boolean v0, v0, Lolive/app/OliveUi$Pull;->loading:Z

    return v0
.end method

.method public show()V
    .locals 1

    .prologue
    .line 97
    iget-object v0, p0, Lolive/app/OliveUi$InlineProgress;->pull:Lolive/app/OliveUi$Pull;

    invoke-virtual {v0}, Lolive/app/OliveUi$Pull;->startLoading()V

    .line 98
    return-void
.end method
