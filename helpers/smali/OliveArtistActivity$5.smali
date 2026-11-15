.class Lolive/app/OliveArtistActivity$5;
.super Ljava/lang/Object;
.source "OliveArtistActivity.java"

# interfaces
.implements Landroid/widget/AdapterView$OnItemClickListener;


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
    .line 84
    iput-object p1, p0, Lolive/app/OliveArtistActivity$5;->this$0:Lolive/app/OliveArtistActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onItemClick(Landroid/widget/AdapterView;Landroid/view/View;IJ)V
    .locals 5
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/widget/AdapterView",
            "<*>;",
            "Landroid/view/View;",
            "IJ)V"
        }
    .end annotation

    .prologue
    .line 87
    new-instance v1, Landroid/os/Bundle;

    invoke-direct {v1}, Landroid/os/Bundle;-><init>()V

    .line 88
    const-string v2, "objId"

    iget-object v0, p0, Lolive/app/OliveArtistActivity$5;->this$0:Lolive/app/OliveArtistActivity;

    invoke-static {v0}, Lolive/app/OliveArtistActivity;->access$000(Lolive/app/OliveArtistActivity;)Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0, p3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    invoke-virtual {v1, v2, v0}, Landroid/os/Bundle;->putString(Ljava/lang/String;Ljava/lang/String;)V

    .line 89
    const-string v2, "albumTitle"

    iget-object v0, p0, Lolive/app/OliveArtistActivity$5;->this$0:Lolive/app/OliveArtistActivity;

    invoke-static {v0}, Lolive/app/OliveArtistActivity;->access$100(Lolive/app/OliveArtistActivity;)Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    if-ge p3, v0, :cond_0

    iget-object v0, p0, Lolive/app/OliveArtistActivity$5;->this$0:Lolive/app/OliveArtistActivity;

    invoke-static {v0}, Lolive/app/OliveArtistActivity;->access$100(Lolive/app/OliveArtistActivity;)Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0, p3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    :goto_0
    invoke-virtual {v1, v2, v0}, Landroid/os/Bundle;->putString(Ljava/lang/String;Ljava/lang/String;)V

    .line 90
    iget-object v0, p0, Lolive/app/OliveArtistActivity$5;->this$0:Lolive/app/OliveArtistActivity;

    new-instance v2, Landroid/content/Intent;

    invoke-direct {v2}, Landroid/content/Intent;-><init>()V

    iget-object v3, p0, Lolive/app/OliveArtistActivity$5;->this$0:Lolive/app/OliveArtistActivity;

    const-string v4, "olive.app.TrackListTab"

    invoke-virtual {v2, v3, v4}, Landroid/content/Intent;->setClassName(Landroid/content/Context;Ljava/lang/String;)Landroid/content/Intent;

    move-result-object v2

    invoke-virtual {v2, v1}, Landroid/content/Intent;->putExtras(Landroid/os/Bundle;)Landroid/content/Intent;

    move-result-object v1

    invoke-virtual {v0, v1}, Lolive/app/OliveArtistActivity;->startActivity(Landroid/content/Intent;)V

    .line 91
    return-void

    .line 89
    :cond_0
    const-string v0, ""

    goto :goto_0
.end method
