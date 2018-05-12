// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

/** Class representing a registered user. */
public class User {
  private final UUID id;
  private final String name;
  private final String password;
  private final Instant creation;
  private String profilePic;
  private String bio;
  private String status;

  /**
   * Constructs a new User.
   *
   * @param id the ID of this User
   * @param name the username of this User
   * @param password the password of this User
   * @param creation the creation time of this User
   */
  public User(UUID id, String name, String password, Instant creation) {
    this.id = id;
    this.name = name;
	this.password = password;
    this.creation = creation;
    this.profilePic = "";
    this.bio = "";
    this.status = "";
  }

  /** Overloaded Constructor */
  /**
   * Constructs a new User.
   *
   * @param id the ID of this User
   * @param name the username of this User
   * @param password the password of this User
   * @param creation the creation time of this User
   * @param profilePic the creation time of this User
   */
  public User(UUID id, String name, String password, Instant creation, String profilePic, String bio, String status) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.creation = creation;
    this.profilePic = profilePic;
    this.bio = bio;
    this.status = status;
  }

  /** Returns the ID of this User. */
  public UUID getId() {
    return id;
  }

  /** Returns the username of this User. */
  public String getName() {
    return name;
  }

  /** Returns the password of this User. */
  public String getPassword() {
	  return password;
  }

  /** Returns the creation time of this User. */
  public Instant getCreationTime() {
    return creation;
  }

  /** Returns the blobKey string of this User. */
  public String getProfilePic() {
    return profilePic;
  }

  /** Sets the blobKey string of this User. */
  public void setProfilePic(String pictureKey){ this.profilePic = pictureKey;}

  public String getUserBio() { return bio;  }

  public void setUserBio(String bioKey){ this.bio = bioKey;}

  public String getUserStatus() { return status;  }

  public void setUserStatus(String statusKey){ this.status = statusKey;}

}
