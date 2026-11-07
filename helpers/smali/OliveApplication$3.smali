.class Lolive/app/OliveApplication$3;
.super Ljava/lang/Object;
.source "OliveApplication.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveApplication;->showLastCrash(Landroid/app/Activity;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lolive/app/OliveApplication;

.field final synthetic val$app:Landroid/content/Context;

.field final synthetic val$report:Ljava/lang/String;


# direct methods
.method constructor <init>(Lolive/app/OliveApplication;Landroid/content/Context;Ljava/lang/String;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 137
    iput-object p1, p0, Lolive/app/OliveApplication$3;->this$0:Lolive/app/OliveApplication;

    iput-object p2, p0, Lolive/app/OliveApplication$3;->val$app:Landroid/content/Context;

    iput-object p3, p0, Lolive/app/OliveApplication$3;->val$report:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 3

    .prologue
    .line 139
    iget-object v0, p0, Lolive/app/OliveApplication$3;->val$app:Landroid/content/Context;

    const-string v1, "clipboard"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/content/ClipboardManager;

    .line 140
    const-string v1, "Olive crash report"

    iget-object v2, p0, Lolive/app/OliveApplication$3;->val$report:Ljava/lang/String;

    invoke-static {v1, v2}, Landroid/content/ClipData;->newPlainText(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Landroid/content/ClipData;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/content/ClipboardManager;->setPrimaryClip(Landroid/content/ClipData;)V

    .line 141
    iget-object v0, p0, Lolive/app/OliveApplication$3;->val$app:Landroid/content/Context;

    const-string v1, "Crash report copied"

    const/4 v2, 0x0

    invoke-static {v0, v1, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    .line 142
    return-void
.end method
