package generator

import java.util.Date

object Model {
  object nodes {
    case class User(label: String = "user",
                    userId: String,
                    screenName: String,
                    userUrl: String,
                    description: String,
                    postCard: String,
                    friendsCard: String,
                    personalCard: String,
                    partnerCard: String,
                    countersCard: String,
                    photosCard: String)

    case class PhotosGallery(label: String = "photosGallery", galleryId: String)

    case class Photo(label: String = "photo",
                     photoName: String,
                     photoId: String,
                     sizeInKb: Long,
                     weight: Long,
                     height: Long)

    case class PostCard(label: String = "photosCard", userId: String, postIds: List[String])

    case class MailBox(label: String = "mailbox", mailboxId: String)

    case class Message(label: String = "post",
                       postId: String,
                       authorId: String,
                       text: String,
                       createdDate: Int,
                       weight: Int,
                       height: Int
                   )


    case class FriendsCard(label: String = "friendsCard", userId: String, friendsIds: List[String])

    case class PersonalInformation(label: String = "personalCard",
                                   personalCardId: String,
                                   createdAt: Date,
                                   firstName: String,
                                   birthDate: Date,
                                   lastName: String,
                                   phone: String,
                                   email: String,
                                   age: Int,
                                   gender: String)

    case class AddressCard(label: String = "addressCard",
                           addressId: String,
                           userId: String,
                           country: String,
                           state: String,
                           city: String,
                           street: String)
  }

  object edges {
    type Card = String;
    type Id = String;

    case class HasPartner(label: String = "hasPartner", from: Id, to: Id)

    case class HasPhotos(label: String = "hasPhotos", from: Id, to: List[Card])

    case class HasPhotosGallery(label: String = "hasPhotosGallery", from: Id, to: Id)

    case class PhotoInGallery(label: String = "photoInGallery",  from: Id, to: Id)
    
    case class HasMailbox(label: String = "hasMailBox", from: Id, to: Id)

    case class HasPersonalInformation(label: String = "hasContacts", from: Id, to: Card)

    case class HasAddress(label: String = "hasAddress", from: Id, to: Card)
  }
}



