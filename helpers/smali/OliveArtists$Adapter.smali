.class final Lolive/app/OliveArtists$Adapter;
.super Landroid/widget/BaseAdapter;
.source "OliveArtists.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lolive/app/OliveArtists;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x18
    name = "Adapter"
.end annotation


# instance fields
.field private final activity:Landroid/app/Activity;

.field artists:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List",
            "<",
            "Lolive/app/OliveArtists$Artist;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method constructor <init>(Landroid/app/Activity;Ljava/util/List;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/app/Activity;",
            "Ljava/util/List",
            "<",
            "Lolive/app/OliveArtists$Artist;",
            ">;)V"
        }
    .end annotation

    .prologue
    .line 163
    invoke-direct {p0}, Landroid/widget/BaseAdapter;-><init>()V

    .line 164
    iput-object p1, p0, Lolive/app/OliveArtists$Adapter;->activity:Landroid/app/Activity;

    .line 165
    iput-object p2, p0, Lolive/app/OliveArtists$Adapter;->artists:Ljava/util/List;

    .line 166
    return-void
.end method


# virtual methods
.method public getCount()I
    .locals 1

    .prologue
    .line 169
    iget-object v0, p0, Lolive/app/OliveArtists$Adapter;->artists:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    return v0
.end method

.method public getItem(I)Ljava/lang/Object;
    .locals 1

    .prologue
    .line 173
    iget-object v0, p0, Lolive/app/OliveArtists$Adapter;->artists:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    return-object v0
.end method

.method public getItemId(I)J
    .locals 2

    .prologue
    .line 177
    int-to-long v0, p1

    return-wide v0
.end method

.method public getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    .locals 5

    .prologue
    const/4 v4, 0x0

    .line 181
    if-nez p2, :cond_0

    .line 182
    iget-object v0, p0, Lolive/app/OliveArtists$Adapter;->activity:Landroid/app/Activity;

    invoke-static {v0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v0

    iget-object v1, p0, Lolive/app/OliveArtists$Adapter;->activity:Landroid/app/Activity;

    const-string v2, "layout"

    const-string v3, "artist_item"

    invoke-static {v1, v2, v3}, Lolive/app/OliveArtists;->res(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {v0, v1, p3, v4}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p2

    .line 184
    :cond_0
    iget-object v0, p0, Lolive/app/OliveArtists$Adapter;->artists:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lolive/app/OliveArtists$Artist;

    .line 185
    iget-object v1, p0, Lolive/app/OliveArtists$Adapter;->activity:Landroid/app/Activity;

    const-string v2, "id"

    const-string v3, "artist"

    invoke-static {v1, v2, v3}, Lolive/app/OliveArtists;->res(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {p2, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    iget-object v2, v0, Lolive/app/OliveArtists$Artist;->name:Ljava/lang/String;

    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 186
    iget-object v1, p0, Lolive/app/OliveArtists$Adapter;->activity:Landroid/app/Activity;

    const-string v2, "id"

    const-string v3, "album"

    invoke-static {v1, v2, v3}, Lolive/app/OliveArtists;->res(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {p2, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    .line 187
    invoke-virtual {v1, v4}, Landroid/widget/TextView;->setVisibility(I)V

    .line 188
    iget-object v2, v0, Lolive/app/OliveArtists$Artist;->ids:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v2

    const/4 v3, 0x1

    if-ne v2, v3, :cond_1

    iget-object v2, v0, Lolive/app/OliveArtists$Artist;->titles:Ljava/util/ArrayList;

    invoke-virtual {v2, v4}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/CharSequence;

    :goto_0
    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 189
    iget-object v1, p0, Lolive/app/OliveArtists$Adapter;->activity:Landroid/app/Activity;

    const-string v2, "id"

    const-string v3, "image"

    invoke-static {v1, v2, v3}, Lolive/app/OliveArtists;->res(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {p2, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/ImageView;

    invoke-virtual {v0}, Lolive/app/OliveArtists$Artist;->cover()Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Lolive/app/OliveArtists;->bindArt(Landroid/widget/ImageView;Ljava/lang/String;)V

    .line 190
    return-object p2

    .line 188
    :cond_1
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v3, v0, Lolive/app/OliveArtists$Artist;->ids:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " albums"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    goto :goto_0
.end method
