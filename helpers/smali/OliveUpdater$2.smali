.class Lolive/app/OliveUpdater$2;
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
    .line 209
    iput-object p1, p0, Lolive/app/OliveUpdater$2;->val$activity:Landroid/app/Activity;

    iput-object p2, p0, Lolive/app/OliveUpdater$2;->val$release:Lolive/app/OliveUpdater$Release;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 3

    .prologue
    .line 211
    iget-object v0, p0, Lolive/app/OliveUpdater$2;->val$activity:Landroid/app/Activity;

    const-string v1, "Olive_Settings"

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/app/Activity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v0

    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    const-string v1, "update_skipped"

    iget-object v2, p0, Lolive/app/OliveUpdater$2;->val$release:Lolive/app/OliveUpdater$Release;

    iget-object v2, v2, Lolive/app/OliveUpdater$Release;->version:Ljava/lang/String;

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->apply()V

    .line 212
    return-void
.end method
