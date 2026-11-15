.class Lolive/app/OliveArtistActivity$2;
.super Ljava/lang/Object;
.source "OliveArtistActivity.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveArtistActivity;->onCreate(Landroid/os/Bundle;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lolive/app/OliveArtistActivity;


# direct methods
.method constructor <init>(Lolive/app/OliveArtistActivity;)V
    .locals 0

    .prologue
    .line 45
    iput-object p1, p0, Lolive/app/OliveArtistActivity$2;->this$0:Lolive/app/OliveArtistActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 4

    .prologue
    .line 47
    iget-object v0, p0, Lolive/app/OliveArtistActivity$2;->this$0:Lolive/app/OliveArtistActivity;

    new-instance v1, Landroid/content/Intent;

    invoke-direct {v1}, Landroid/content/Intent;-><init>()V

    iget-object v2, p0, Lolive/app/OliveArtistActivity$2;->this$0:Lolive/app/OliveArtistActivity;

    const-string v3, "olive.app.LoadTabs"

    invoke-virtual {v1, v2, v3}, Landroid/content/Intent;->setClassName(Landroid/content/Context;Ljava/lang/String;)Landroid/content/Intent;

    move-result-object v1

    const/high16 v2, 0x24000000

    .line 48
    invoke-virtual {v1, v2}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    move-result-object v1

    .line 47
    invoke-virtual {v0, v1}, Lolive/app/OliveArtistActivity;->startActivity(Landroid/content/Intent;)V

    .line 49
    iget-object v0, p0, Lolive/app/OliveArtistActivity$2;->this$0:Lolive/app/OliveArtistActivity;

    invoke-virtual {v0}, Lolive/app/OliveArtistActivity;->finish()V

    .line 50
    return-void
.end method
