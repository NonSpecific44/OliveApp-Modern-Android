.class Lolive/app/OliveUpdater$6;
.super Ljava/lang/Object;
.source "OliveUpdater.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveUpdater;->attachSettings(Landroid/app/Activity;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$activity:Landroid/app/Activity;


# direct methods
.method constructor <init>(Landroid/app/Activity;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 438
    iput-object p1, p0, Lolive/app/OliveUpdater$6;->val$activity:Landroid/app/Activity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 2

    .prologue
    .line 440
    iget-object v0, p0, Lolive/app/OliveUpdater$6;->val$activity:Landroid/app/Activity;

    const/4 v1, 0x1

    invoke-static {v0, v1}, Lolive/app/OliveUpdater;->check(Landroid/app/Activity;Z)V

    .line 441
    return-void
.end method
