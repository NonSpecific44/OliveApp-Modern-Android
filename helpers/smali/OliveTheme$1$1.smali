.class Lolive/app/OliveTheme$1$1;
.super Ljava/lang/Object;
.source "OliveTheme.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveTheme$1;->onClick(Landroid/view/View;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lolive/app/OliveTheme$1;


# direct methods
.method constructor <init>(Lolive/app/OliveTheme$1;)V
    .locals 0

    .prologue
    .line 119
    iput-object p1, p0, Lolive/app/OliveTheme$1$1;->this$0:Lolive/app/OliveTheme$1;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 2

    .prologue
    .line 121
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 122
    iget-object v0, p0, Lolive/app/OliveTheme$1$1;->this$0:Lolive/app/OliveTheme$1;

    iget-object v0, v0, Lolive/app/OliveTheme$1;->val$themeValue:Landroid/widget/TextView;

    invoke-static {}, Lolive/app/OliveTheme;->access$000()[Ljava/lang/String;

    move-result-object v1

    aget-object v1, v1, p2

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 123
    iget-object v0, p0, Lolive/app/OliveTheme$1$1;->this$0:Lolive/app/OliveTheme$1;

    iget-object v0, v0, Lolive/app/OliveTheme$1;->val$activity:Landroid/app/Activity;

    invoke-static {v0, p2}, Lolive/app/OliveTheme;->setThemeMode(Landroid/content/Context;I)V

    .line 124
    return-void
.end method
