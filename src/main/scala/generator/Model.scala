package generator

import java.time.LocalDate
import java.util.Date

object Model {
  object nodes {
    case class User(label: String = "user",
                    userId: String,
                    screenName: String,
                    userUrl: String,
                    mailboxId: String,
                    description: String,
                    friendsCard: String,
                    personalCard: String,
                    partnerId: String,
                    countersCard: String
                   )


    case class PhotosGallery(label: String = "photosGallery", galleryId: String)

    case class Photo(label: String = "photo",
                     photoName: String,
                     photoId: String,
                     sizeInKb: Long,
                     weight: Long,
                     height: Long)

    case class PostCard(label: String = "photosCard", userId: String, postIds: List[String])

    case class MailBox(label: String = "mailbox", mailboxId: String)

    case class Message(label: String = "post", messageId: String, text: String, createdDate: LocalDate)

    case class FriendsCard(label: String = "friendsCard", userId: String, friendsIds: List[String], cardId: String)

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

    case class PhotoInGallery(label: String = "photoInGallery", from: Id, to: Id)

    case class MessageInMailBox(label: String = "hasMailBox", from: Id, to: Id)

    case class HasMailBox(label: String = "messageInMailBox", from: Id, to: Id)

    case class HasPersonalInformation(label: String = "hasContacts", from: Id, to: Card)

    case class HasAddress(label: String = "hasAddress", from: Id, to: Card)

    case class MessageFromTo(label: String = "messageFromTo", from: Id, to: Id)
  }
}



