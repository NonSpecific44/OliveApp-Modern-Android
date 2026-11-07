.class Lolive/app/OliveTheme$1;
.super Ljava/lang/Object;
.source "OliveTheme.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveTheme;->attachSettings(Landroid/app/Activity;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$activity:Landroid/app/Activity;

.field final synthetic val$themeValue:Landroid/widget/TextView;


# direct methods
.method constructor <init>(Landroid/app/Activity;Landroid/widget/TextView;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 115
    iput-object p1, p0, Lolive/app/OliveTheme$1;->val$activity:Landroid/app/Activity;

    iput-object p2, p0, Lolive/app/OliveTheme$1;->val$themeValue:Landroid/widget/TextView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 4

    .prologue
    .line 117
    new-instance v0, Landroid/app/AlertDialog$Builder;

    iget-object v1, p0, Lolive/app/OliveTheme$1;->val$activity:Landroid/app/Activity;

    invoke-direct {v0, v1}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    const-string v1, "Theme"

    .line 118
    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    .line 119
    invoke-static {}, Lolive/app/OliveTheme;->access$000()[Ljava/lang/String;

    move-result-object v1

    iget-object v2, p0, Lolive/app/OliveTheme$1;->val$activity:Landroid/app/Activity;

    invoke-static {v2}, Lolive/app/OliveTheme;->themeMode(Landroid/content/Context;)I

    move-result v2

    new-instance v3, Lolive/app/OliveTheme$1$1;

    invoke-direct {v3, p0}, Lolive/app/OliveTheme$1$1;-><init>(Lolive/app/OliveTheme$1;)V

    invoke-virtual {v0, v1, v2, v3}, Landroid/app/AlertDialog$Builder;->setSingleChoiceItems([Ljava/lang/CharSequence;ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const-string v1, "Cancel"

    const/4 v2, 0x0

    .line 126
    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    .line 127
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->show()Landroid/app/AlertDialog;

    .line 128
    return-void
.end method
