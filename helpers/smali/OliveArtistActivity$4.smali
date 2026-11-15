.class Lolive/app/OliveArtistActivity$4;
.super Landroid/widget/BaseAdapter;
.source "OliveArtistActivity.java"


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
    .line 61
    iput-object p1, p0, Lolive/app/OliveArtistActivity$4;->this$0:Lolive/app/OliveArtistActivity;

    invoke-direct {p0}, Landroid/widget/BaseAdapter;-><init>()V

    return-void
.end method


# virtual methods
.method public getCount()I
    .locals 1

    .prologue
    .line 63
    iget-object v0, p0, Lolive/app/OliveArtistActivity$4;->this$0:Lolive/app/OliveArtistActivity;

    invoke-static {v0}, Lolive/app/OliveArtistActivity;->access$000(Lolive/app/OliveArtistActivity;)Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    return v0
.end method

.method public getItem(I)Ljava/lang/Object;
    .locals 1

    .prologue
    .line 67
    iget-object v0, p0, Lolive/app/OliveArtistActivity$4;->this$0:Lolive/app/OliveArtistActivity;

    invoke-static {v0}, Lolive/app/OliveArtistActivity;->access$100(Lolive/app/OliveArtistActivity;)Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    return-object v0
.end method

.method public getItemId(I)J
    .locals 2

    .prologue
    .line 71
    int-to-long v0, p1

    return-wide v0
.end method

.method public getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    .locals 4

    .prologue
    .line 75
    if-nez p2, :cond_0

    .line 76
    iget-object v0, p0, Lolive/app/OliveArtistActivity$4;->this$0:Lolive/app/OliveArtistActivity;

    invoke-static {v0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v0

    iget-object v1, p0, Lolive/app/OliveArtistActivity$4;->this$0:Lolive/app/OliveArtistActivity;

    const-string v2, "layout"

    const-string v3, "album_item"

    invoke-static {v1, v2, v3}, Lolive/app/OliveArtistActivity;->access$200(Lolive/app/OliveArtistActivity;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    const/4 v2, 0x0

    invoke-virtual {v0, v1, p3, v2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p2

    .line 78
    :cond_0
    iget-object v0, p0, Lolive/app/OliveArtistActivity$4;->this$0:Lolive/app/OliveArtistActivity;

    const-string v1, "id"

    const-string v2, "album"

    invoke-static {v0, v1, v2}, Lolive/app/OliveArtistActivity;->access$200(Lolive/app/OliveArtistActivity;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p2, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iget-object v1, p0, Lolive/app/OliveArtistActivity$4;->this$0:Lolive/app/OliveArtistActivity;

    invoke-static {v1}, Lolive/app/OliveArtistActivity;->access$100(Lolive/app/OliveArtistActivity;)Ljava/util/ArrayList;

    move-result-object v1

    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v1

    if-ge p1, v1, :cond_1

    iget-object v1, p0, Lolive/app/OliveArtistActivity$4;->this$0:Lolive/app/OliveArtistActivity;

    invoke-static {v1}, Lolive/app/OliveArtistActivity;->access$100(Lolive/app/OliveArtistActivity;)Ljava/util/ArrayList;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/CharSequence;

    :goto_0
    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 79
    iget-object v0, p0, Lolive/app/OliveArtistActivity$4;->this$0:Lolive/app/OliveArtistActivity;

    const-string v1, "id"

    const-string v2, "artist"

    invoke-static {v0, v1, v2}, Lolive/app/OliveArtistActivity;->access$200(Lolive/app/OliveArtistActivity;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p2, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    .line 80
    iget-object v0, p0, Lolive/app/OliveArtistActivity$4;->this$0:Lolive/app/OliveArtistActivity;

    const-string v1, "id"

    const-string v2, "image"

    invoke-static {v0, v1, v2}, Lolive/app/OliveArtistActivity;->access$200(Lolive/app/OliveArtistActivity;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p2, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iget-object v1, p0, Lolive/app/OliveArtistActivity$4;->this$0:Lolive/app/OliveArtistActivity;

    invoke-static {v1}, Lolive/app/OliveArtistActivity;->access$300(Lolive/app/OliveArtistActivity;)Ljava/util/ArrayList;

    move-result-object v1

    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v1

    if-ge p1, v1, :cond_2

    iget-object v1, p0, Lolive/app/OliveArtistActivity$4;->this$0:Lolive/app/OliveArtistActivity;

    invoke-static {v1}, Lolive/app/OliveArtistActivity;->access$300(Lolive/app/OliveArtistActivity;)Ljava/util/ArrayList;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    :goto_1
    invoke-static {v0, v1}, Lolive/app/OliveArtists;->bindArt(Landroid/widget/ImageView;Ljava/lang/String;)V

    .line 81
    return-object p2

    .line 78
    :cond_1
    const-string v1, ""

    goto :goto_0

    .line 80
    :cond_2
    const-string v1, ""

    goto :goto_1
.end method
