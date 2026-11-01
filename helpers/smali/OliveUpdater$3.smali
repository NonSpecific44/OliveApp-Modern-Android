.class Lolive/app/OliveUpdater$3;
.super Ljava/lang/Object;
.source "OliveUpdater.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveUpdater;->offer(Landroid/app/Activity;Lolive/app/OliveUpdater$Release;Ljava/lang/String;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$activity:Landroid/app/Activity;

.field final synthetic val$release:Lolive/app/OliveUpdater$Release;


# direct methods
.method constructor <init>(Landroid/app/Activity;Lolive/app/OliveUpdater$Release;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 203
    iput-object p1, p0, Lolive/app/OliveUpdater$3;->val$activity:Landroid/app/Activity;

    iput-object p2, p0, Lolive/app/OliveUpdater$3;->val$release:Lolive/app/OliveUpdater$Release;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 2

    .prologue
    .line 205
    iget-object v0, p0, Lolive/app/OliveUpdater$3;->val$activity:Landroid/app/Activity;

    iget-object v1, p0, Lolive/app/OliveUpdater$3;->val$release:Lolive/app/OliveUpdater$Release;

    invoke-static {v0, v1}, Lolive/app/OliveUpdater;->access$200(Landroid/app/Activity;Lolive/app/OliveUpdater$Release;)V

    .line 206
    return-void
.end method
