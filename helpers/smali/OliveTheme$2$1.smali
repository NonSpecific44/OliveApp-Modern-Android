.class Lolive/app/OliveTheme$2$1;
.super Ljava/lang/Object;
.source "OliveTheme.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveTheme$2;->onClick(Landroid/view/View;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lolive/app/OliveTheme$2;

.field final synthetic val$was:I


# direct methods
.method constructor <init>(Lolive/app/OliveTheme$2;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 135
    iput-object p1, p0, Lolive/app/OliveTheme$2$1;->this$0:Lolive/app/OliveTheme$2;

    iput p2, p0, Lolive/app/OliveTheme$2$1;->val$was:I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 2

    .prologue
    const/4 v0, 0x1

    .line 137
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 138
    iget v1, p0, Lolive/app/OliveTheme$2$1;->val$was:I

    if-eq p2, v1, :cond_0

    .line 139
    iget-object v1, p0, Lolive/app/OliveTheme$2$1;->this$0:Lolive/app/OliveTheme$2;

    iget-object v1, v1, Lolive/app/OliveTheme$2;->val$activity:Landroid/app/Activity;

    if-ne p2, v0, :cond_1

    :goto_0
    invoke-static {v1, v0}, Lolive/app/OliveTheme;->setOlive(Landroid/app/Activity;Z)V

    .line 141
    :cond_0
    return-void

    .line 139
    :cond_1
    const/4 v0, 0x0

    goto :goto_0
.end method
